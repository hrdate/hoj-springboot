package com.hrdate.oj.service.problem;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.constant.Constants;
import com.hrdate.oj.entity.judge.Judge;
import com.hrdate.oj.entity.problem.Problem;
import com.hrdate.oj.entity.problem.ProblemLanguage;
import com.hrdate.oj.entity.problem.ProblemTag;
import com.hrdate.oj.entity.problem.Tag;
import com.hrdate.oj.enums.OJAccessEnum;
import com.hrdate.oj.exceptions.ServiceException;
import com.hrdate.oj.mapper.ProblemMapper;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.dto.LastAcceptedCodeVO;
import com.hrdate.oj.pojo.dto.PidListDTO;
import com.hrdate.oj.pojo.dto.UserDetailDTO;
import com.hrdate.oj.pojo.vo.*;
import com.hrdate.oj.service.contest.ContestService;
import com.hrdate.oj.service.judege.JudgeService;
import com.hrdate.oj.utils.TokenUtil;
import com.hrdate.oj.validator.AccessValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 题目
 * @author: huangrendi
 * @date: 2022-11-26
 **/

@Slf4j(topic = "题目详情")
@Service
public class ProblemService extends ServiceImpl<ProblemMapper, Problem> {

    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private JudgeService judgeService;
    @Autowired
    private ProblemTagService problemTagService;
    @Autowired
    private TagService tagService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private ProblemLanguageService problemLanguageService;
    /**
     * 获取题目列表分页
     */
    public CommonResult<Page<ProblemVO>> getProblemList(Integer limit, Integer currentPage, String keyword, List<Long> tagId, Integer difficulty, String oj) {
        // 页数，每页题数若为空，设置默认值
        if (currentPage == null || currentPage < 1) currentPage = 1;
        if (limit == null || limit < 1) limit = 10;

        // 关键词查询不为空
        if (!StringUtils.isEmpty(keyword)) {
            keyword = keyword.trim();
        }
        if (oj != null && !Constants.RemoteOJ.isRemoteOJ(oj)) {
            oj = "Mine";
        }

        //新建分页
        Page<ProblemVO> page = new Page<>(currentPage, limit);
        Integer tagListSize = null;
        if (tagId != null) {
            tagId = tagId.stream().distinct().collect(Collectors.toList());
            tagListSize = tagId.size();
        }

        List<ProblemVO> problemList = problemMapper.getProblemList(page, null, keyword, difficulty, tagId, tagListSize, oj);
        if (problemList.size() > 0) {
            List<Long> pidList = problemList.stream().map(ProblemVO::getPid).collect(Collectors.toList());
            List<ProblemCountVO> problemListCount = judgeService.getProblemListCount(pidList);
            for (ProblemVO problemVo : problemList) {
                for (ProblemCountVO problemCountVo : problemListCount) {
                    if (problemVo.getPid().equals(problemCountVo.getPid())) {
                        problemVo.setProblemCountVo(problemCountVo);
                        break;
                    }
                }
            }
        }
        page.setRecords(problemList);

        return CommonResult.succeedResult(page);
    }

    /**
     * 随机选取一道题目
     */
    public CommonResult<RandomProblemVO> getRandomProblem() {
        // 必须是公开题目
        LambdaQueryWrapper<Problem> wrapper = new LambdaQueryWrapper<Problem>()
                .eq(Problem::getAuth, 1)
                .select(Problem::getProblemId);
        List<Problem> list = this.list(wrapper);
        if (list.size() == 0) {
            throw new ServiceException("获取随机题目失败，题库暂无公开题目！");
        }
        int index = new Random().nextInt(list.size());
        RandomProblemVO randomProblemVo = new RandomProblemVO();
        randomProblemVo.setProblemId(list.get(index).getProblemId());
        return CommonResult.succeedResult(randomProblemVo);
    }

    /**
     * 获取用户对应该题目列表中各个题目的做题情况
     * @param pidListDto
     * @return
     */
    public CommonResult<HashMap<Long, Object>> getUserProblemStatus(PidListDTO pidListDto) {


        return CommonResult.succeedResult();
    }

    /**
     * 获取指定题目的详情信息，标签，所支持语言，做题情况（只能查询公开题目 也就是auth为1）
     * @param problemId
     * @param gid
     * @return
     */
    public CommonResult<ProblemInfoVO> getProblemInfo(String problemId, Long gid) {
        LambdaQueryWrapper<Problem> wrapper = new LambdaQueryWrapper<Problem>()
                .eq(Problem::getProblemId, problemId);
        //查询题目详情，题目标签，题目语言，题目做题情况
        Problem problem = this.getOne(wrapper, false);
        if (problem == null) {
            throw new ServiceException("该题号对应的题目不存在");
        }
        if (problem.getAuth() != 1) {
            throw new ServiceException("该题号对应题目并非公开题目，不支持访问！");
        }

        QueryWrapper<ProblemTag> problemTagQueryWrapper = new QueryWrapper<>();
        problemTagQueryWrapper.eq("pid", problem.getId());

        // 获取该题号对应的标签id
        List<Long> tidList = new LinkedList<>();
        problemTagService.list(problemTagQueryWrapper).forEach(problemTag -> {
            tidList.add(problemTag.getTid());
        });
        List<Tag> tags = new ArrayList<>();
        if (tidList.size() > 0) {
            tags = (List<Tag>) tagService.listByIds(tidList);
        }

        // 记录 languageId对应的name
        HashMap<Long, String> tmpMap = new HashMap<>();
        // 获取题目提交的代码支持的语言
        List<String> languagesStr = new LinkedList<>();
        QueryWrapper<ProblemLanguage> problemLanguageQueryWrapper = new QueryWrapper<>();
        problemLanguageQueryWrapper.eq("pid", problem.getId()).select("lid");
        List<Long> lidList = problemLanguageService.list(problemLanguageQueryWrapper)
                .stream().map(ProblemLanguage::getLid).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(lidList)) {
            languageService.listByIds(lidList).forEach(language -> {
                languagesStr.add(language.getName());
                tmpMap.put(language.getId(), language.getName());
            });
        }
        // 获取题目的提交记录
        ProblemCountVO problemCount = judgeService.getProblemCount(problem.getId(), gid);

        // 屏蔽一些题目参数
        problem.setJudgeExtraFile(null)
                .setSpjCode(null)
                .setSpjLanguage(null);

        return CommonResult.succeedResult(new ProblemInfoVO(problem, tags, languagesStr, problemCount));
    }

    /**
     * 获取用户对于该题最近AC的代码
     * @param pid
     * @param cid
     * @return
     */
    public CommonResult<LastAcceptedCodeVO> getUserLastAcceptedCode(Long pid, Long cid) {
        UserDetailDTO loginUser = TokenUtil.getLoginUser();
        QueryWrapper<Judge> wrapper = new QueryWrapper<Judge>()
                .select("submit_id", "cid", "code", "username", "submit_time", "language")
                .eq("uid", loginUser.getUid())
                .eq("pid", pid)
                .eq(cid != null,"cid", cid)
                .eq("status", 0)
                .orderByDesc("submit_id")
                .last("limit 1");
        List<Judge> judgeList = judgeService.list(wrapper);
        LastAcceptedCodeVO lastAcceptedCodeVO = new LastAcceptedCodeVO();
        if (CollectionUtil.isNotEmpty(judgeList)) {
            Judge judge = judgeList.get(0);
            lastAcceptedCodeVO.setSubmitId(judge.getSubmitId());
            lastAcceptedCodeVO.setLanguage(judge.getLanguage());
            lastAcceptedCodeVO.setCode(buildCode(judge));
        } else {
            lastAcceptedCodeVO.setCode("");
        }
        return CommonResult.succeedResult();
    }

    private String buildCode(Judge judge) {
        if (judge.getCid() == null || judge.getCid() == 0) {
            // 比赛外的提交代码 如果不是超管或题目管理员，需要检查网站是否开启隐藏代码功能
            Boolean isAdmin = TokenUtil.checkCurrentUserRole("admin");
            // 是否为题目管理员
            if (!isAdmin) {
                try {
                    AccessValidator.validateAccess(OJAccessEnum.HIDE_NON_CONTEST_SUBMISSION_CODE);
                } catch (ServiceException e) {
                    return "Because the super administrator has enabled " +
                            "the function of not viewing the submitted code outside the contest of master station, \n" +
                            "the code of this submission details has been hidden.";
                }
            }
        }
        if (!judge.getLanguage().toLowerCase().contains("py")) {
            return judge.getCode() + "\n\n" +
                    "/**\n" +
                    "* @runId: " + judge.getSubmitId() + "\n" +
                    "* @language: " + judge.getLanguage() + "\n" +
                    "* @author: " + judge.getUsername() + "\n" +
                    "* @submitTime: " + DateUtil.format(judge.getSubmitTime(), "yyyy-MM-dd HH:mm:ss") + "\n" +
                    "*/";
        } else {
            return judge.getCode() + "\n\n" +
                    "'''\n" +
                    "    @runId: " + judge.getSubmitId() + "\n" +
                    "    @language: " + judge.getLanguage() + "\n" +
                    "    @author: " + judge.getUsername() + "\n" +
                    "    @submitTime: " + DateUtil.format(judge.getSubmitTime(), "yyyy-MM-dd HH:mm:ss") + "\n" +
                    "'''";
        }
    }

    /**
     * 获取专注模式页面底部的题目列表
     * @param cid
     * @return
     */
    public CommonResult<List<ProblemFullScreenListVO>> getFullScreenProblemList(Long cid) {

        return CommonResult.succeedResult();
    }
}
