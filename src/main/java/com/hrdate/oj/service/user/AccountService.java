package com.hrdate.oj.service.user;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import com.hrdate.oj.entity.problem.Problem;
import com.hrdate.oj.entity.user.UserAcProblem;
import com.hrdate.oj.entity.user.UserAuth;
import com.hrdate.oj.entity.user.UserInfo;
import com.hrdate.oj.exceptions.ServiceException;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.dto.CheckUsernameOrEmailDTO;
import com.hrdate.oj.pojo.dto.UserDetailDTO;
import com.hrdate.oj.pojo.dto.UserInfoChangeDTO;
import com.hrdate.oj.pojo.vo.CheckUsernameOrEmailVO;
import com.hrdate.oj.pojo.vo.UserHomeProblemVO;
import com.hrdate.oj.pojo.vo.UserHomeVO;
import com.hrdate.oj.pojo.vo.UserInfoVO;
import com.hrdate.oj.service.problem.ProblemService;
import com.hrdate.oj.utils.BeanCopyUtil;
import com.hrdate.oj.utils.TokenUtil;
import com.hrdate.oj.validator.CommonValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-23
 **/

@Slf4j(topic = "user用户-account账号")
@Service
public class AccountService {


    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserAcProblemService userAcProblemService;
    @Autowired
    private ProblemService problemService;

    /**
     * @MethodName checkUsernameOrEmail
     * @Description 检验用户名和邮箱是否存在
     * @Return
     */
    public CommonResult checkUsernameOrEmail(CheckUsernameOrEmailDTO checkUsernameOrEmailDto) {
        String email = checkUsernameOrEmailDto.getEmail();

        String username = checkUsernameOrEmailDto.getUsername();

        boolean rightEmail = false;

        boolean rightUsername = false;

        if (StringUtils.isNotBlank(email)) {
            email = email.trim();
            boolean isEmail = Validator.isEmail(email);
            LambdaQueryWrapper<UserAuth> wrapper = new LambdaQueryWrapper<UserAuth>().eq(isEmail, UserAuth::getEmail, email);
            UserAuth userAuth = userAuthService.getOne(wrapper, false);
            rightEmail = userAuth != null;
        }

        if (StringUtils.isNotBlank(username)) {
            username = username.trim();
            LambdaQueryWrapper<UserAuth> wrapper = new LambdaQueryWrapper<UserAuth>().eq(UserAuth::getUsername, username);
            UserAuth userAuth = userAuthService.getOne(wrapper, false);
            rightUsername = userAuth != null;
        }

        CheckUsernameOrEmailVO checkUsernameOrEmailVo = new CheckUsernameOrEmailVO();
        checkUsernameOrEmailVo.setEmail(rightEmail);
        checkUsernameOrEmailVo.setUsername(rightUsername);

        return CommonResult.succeedResult(checkUsernameOrEmailVo);
    }

    /**
     * @param uid
     * @MethodName getUserHomeInfo
     * @Description 前端userHome用户个人主页的数据请求，主要是返回解决题目数，AC的题目列表，提交总数，AC总数，Rating分，
     */
    public CommonResult<UserHomeVO> getUserHomeInfo(String uid, String username) {
        UserDetailDTO loginUser = TokenUtil.getLoginUser();
        // 如果没有uid和username，默认查询当前登录用户的
        if (StringUtils.isNotBlank(uid)
                && org.apache.commons.lang3.StringUtils.isNotBlank(username)) {
            if (loginUser != null) {
                uid = loginUser.getUid();
            } else {
                throw new ServiceException("请求参数错误：uid和username不能都为空！");
            }
        }
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<UserInfo>()
                .eq(StringUtils.isNotBlank(uid), UserInfo::getUid, uid)
                .or(StringUtils.isBlank(uid))
                .eq(StringUtils.isNotBlank(username), UserInfo::getUsername, username);
        UserInfo userInfo = userInfoService.getOne(userInfoLambdaQueryWrapper);
        if (userInfo == null) {
            throw new ServiceException("用户不存在, 请求参数错误");
        }
        UserHomeVO userHomeVO = BeanCopyUtil.copy(userInfo, UserHomeVO.class);
        QueryWrapper<UserAcProblem> userAcProblemQueryWrapper = new QueryWrapper<UserAcProblem>()
                .eq("uid", uid)
                .select("distinct pid", "submit_id")
                .orderByAsc("submit_id");
        List<UserAcProblem> userAcProblemList = userAcProblemService.list(userAcProblemQueryWrapper);

        List<Long> pidList = userAcProblemList.stream().map(UserAcProblem::getPid).collect(Collectors.toList());
        List<String> disPlayIdList = Lists.newArrayList();

        if (pidList.size() > 0) {
            QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
            problemQueryWrapper.select("id", "problem_id", "difficulty");
            problemQueryWrapper.in("id", pidList);
            List<Problem> problems = problemService.list(problemQueryWrapper);
            Map<Integer, List<UserHomeProblemVO>> map = problems.stream()
                    .map(this::convertProblemVO)
                    .collect(Collectors.groupingBy(UserHomeProblemVO::getDifficulty));
            // 难度=>[P1000,P1001]
            userHomeVO.setSolvedGroupByDifficulty(map);
            disPlayIdList = problems.stream().map(Problem::getProblemId).collect(Collectors.toList());
        }
        // 已解决题目列表
        userHomeVO.setSolvedList(disPlayIdList);

        // 最近一次登陆
        LambdaQueryWrapper<UserAuth> userAuthLambdaQueryWrapper = new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUid, uid)
                        .select(UserAuth::getLastLoginTime);
        UserAuth userAuth = userAuthService.getOne(userAuthLambdaQueryWrapper, false);
        userHomeVO.setRecentLoginTime(userAuth.getLastLoginTime());
        return CommonResult.succeedResult(userHomeVO);
    }

    private UserHomeProblemVO convertProblemVO(Problem problem) {
        return UserHomeProblemVO.builder()
                .problemId(problem.getProblemId())
                .id(problem.getId())
                .difficulty(problem.getDifficulty())
                .build();
    }


    /**
     * 用户修改自己的信息
     * @param userInfoChangeDTO
     * @return
     */
    public CommonResult<?> changeUserInfo(UserInfoChangeDTO userInfoChangeDTO) {
        // 获取当前登录的用户
        UserDetailDTO loginUser = TokenUtil.getLoginUser();
        String uid = loginUser.getUid();
        if(!userInfoChangeDTO.getUid().equals(uid)) {
            throw new ServiceException("当前修改用户信息的身份错误，请校验参数");
        }
        // 校验参数
        String nickname = userInfoChangeDTO.getNickname();
        if (StringUtils.isNotBlank(nickname) && nickname.length() > 20) {
            throw new ServiceException("昵称nickname的长度不能超过20位");
        }
        CommonValidator.validateContent(userInfoChangeDTO.getSignature(), "个性简介");
        CommonValidator.validateContent(userInfoChangeDTO.getSchool(), "学校", 100);
        CommonValidator.validateContent(userInfoChangeDTO.getNumber(), "学号", 200);
        LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<UserInfo>()
                .eq(UserInfo::getUid, uid)
                .set(UserInfo::getGender, userInfoChangeDTO.getGender())
                .set(StringUtils.isNotBlank(userInfoChangeDTO.getNickname()), UserInfo::getNickname, userInfoChangeDTO.getNickname())
                .set(StringUtils.isNotBlank(userInfoChangeDTO.getAvatar()), UserInfo::getAvatar, userInfoChangeDTO.getAvatar())
                .set(StringUtils.isNotBlank(userInfoChangeDTO.getNumber()), UserInfo::getNumber, userInfoChangeDTO.getNumber())
                .set(StringUtils.isNotBlank(userInfoChangeDTO.getSchool()), UserInfo::getSchool, userInfoChangeDTO.getSchool())
                .set(StringUtils.isNotBlank(userInfoChangeDTO.getCourse()), UserInfo::getCourse, userInfoChangeDTO.getCourse())
                .set(StringUtils.isNotBlank(userInfoChangeDTO.getSignature()), UserInfo::getSignature, userInfoChangeDTO.getSignature())
                ;
        boolean update = userInfoService.update(wrapper);
        if(!update) {
            throw new ServiceException("修改用户["+ loginUser.getUsername() +"]信息失败");
        }
        return CommonResult.succeedResult("修改用户信息成功");
    }
}
