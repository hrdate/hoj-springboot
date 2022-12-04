package com.hrdate.oj.validator;

import com.hrdate.oj.config.SwitchConfig;
import com.hrdate.oj.enums.OJAccessEnum;
import com.hrdate.oj.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author Himit_ZH
 * @Date 2022/5/9
 */
@Component
public class AccessValidator {

//    @Autowired
//    private NacosSwitchConfig nacosSwitchConfig;
    @Autowired
    private SwitchConfig switchConfig;

    public void validateAccess(OJAccessEnum hojAccessEnum) throws ServiceException {
        switch (hojAccessEnum) {
            case PUBLIC_DISCUSSION:
                if (!switchConfig.getOpenPublicDiscussion()) {
                    throw new ServiceException("网站当前未开启公开讨论区的功能，不可访问！");
                }
                break;
            case CONTEST_COMMENT:
                if (!switchConfig.getOpenContestComment()) {
                    throw new ServiceException("网站当前未开启比赛评论区的功能，不可访问！");
                }
                break;
            case PUBLIC_JUDGE:
                if (!switchConfig.getOpenPublicJudge()) {
                    throw new ServiceException("网站当前未开启题目评测的功能，禁止提交或调试！");
                }
                break;
            case CONTEST_JUDGE:
                if (!switchConfig.getOpenContestJudge()) {
                    throw new ServiceException("网站当前未开启比赛题目评测的功能，禁止提交或调试！");
                }
                break;
            case HIDE_NON_CONTEST_SUBMISSION_CODE:
                if (switchConfig.getHideNonContestSubmissionCode()) {
                    throw new ServiceException("网站当前开启了隐藏非比赛提交代码不显示的功能！");
                }
        }
    }
}
