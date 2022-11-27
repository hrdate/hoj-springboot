package com.hrdate.oj.service.contest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrdate.oj.constant.Constants;
import com.hrdate.oj.entity.user.UserInfo;
import com.hrdate.oj.exceptions.ServiceException;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.vo.ACMRankVO;
import com.hrdate.oj.service.user.UserAcProblemService;
import com.hrdate.oj.service.user.UserInfoService;
import com.hrdate.oj.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@Service
public class RankService {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserAcProblemService userAcProblemService;
    @Autowired
    private RedisUtil redisUtil;

    // 排行榜缓存时间 60s
    private static final long cacheRankSecond = 60;

    /**
     * 获取排行榜数据
     * @param limit
     * @param currentPage
     * @param searchUser
     * @param type
     * @return
     */
    public CommonResult<IPage> getRankList(Integer limit, Integer currentPage, String searchUser, Integer type) {
        // 页数，每页题数若为空，设置默认值
        if (currentPage == null || currentPage < 1) currentPage = 1;
        if (limit == null || limit < 1) limit = 50;

        List<String> uidList = null;
        if (!StringUtils.isEmpty(searchUser)) {
            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();

            userInfoQueryWrapper.select("uid");

            userInfoQueryWrapper.and(wrapper -> wrapper
                    .like("username", searchUser)
                    .or()
                    .like("nickname", searchUser));

            userInfoQueryWrapper.eq("status", 0);

            uidList = userInfoService.list(userInfoQueryWrapper)
                    .stream()
                    .map(UserInfo::getUid)
                    .collect(Collectors.toList());
        }

        IPage rankList = null;
        // 根据type查询不同类型的排行榜
        if (type.intValue() == Constants.Contest.TYPE_ACM.getCode()) {
            rankList = getACMRankList(limit, currentPage, uidList);
        }  else {
            throw new ServiceException("排行榜类型代码不正确，请使用0(ACM),1(OI)！");
        }
        return CommonResult.succeedResult(rankList);
    }

    private IPage<ACMRankVO> getACMRankList(int limit, int currentPage, List<String> uidList) {

        IPage<ACMRankVO> data = null;
        if (uidList != null) {
            Page<ACMRankVO> page = new Page<>(currentPage, limit);
            if (uidList.size() > 0) {
                data = userAcProblemService.getACMRankList(page, uidList);
            } else {
                data = page;
            }
        } else {
            String key = Constants.Account.ACM_RANK_CACHE.getCode() + "_" + limit + "_" + currentPage;
            data = (IPage<ACMRankVO>) redisUtil.get(key);
            if (data == null) {
                Page<ACMRankVO> page = new Page<>(currentPage, limit);
                data = userAcProblemService.getACMRankList(page, null);
                redisUtil.set(key, data, cacheRankSecond);
            }
        }

        return data;
    }
}
