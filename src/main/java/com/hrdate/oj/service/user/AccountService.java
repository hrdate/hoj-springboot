package com.hrdate.oj.service.user;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import com.hrdate.oj.config.SwitchConfig;
import com.hrdate.oj.config.WebConfig;
import com.hrdate.oj.constant.Constants;
import com.hrdate.oj.entity.problem.Problem;
import com.hrdate.oj.entity.user.UserAcProblem;
import com.hrdate.oj.entity.user.UserAuth;
import com.hrdate.oj.entity.user.UserInfo;
import com.hrdate.oj.exceptions.ServiceException;
import com.hrdate.oj.manager.EmailManager;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.pojo.dto.*;
import com.hrdate.oj.pojo.vo.*;
import com.hrdate.oj.service.problem.ProblemService;
import com.hrdate.oj.utils.BeanCopyUtil;
import com.hrdate.oj.utils.RedisUtil;
import com.hrdate.oj.utils.TokenUtil;
import com.hrdate.oj.validator.CommonValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
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
    private SwitchConfig switchConfig;
    @Autowired
    private WebConfig webConfig;
    @Autowired
    private RedisUtil  redisUtil;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserAcProblemService userAcProblemService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private EmailManager emailManager;



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

    /**
     * 调用邮件服务，发送注册流程的6位随机验证码
     * @param email
     * @return
     */
    public CommonResult<RegisterCodeVO> getRegisterCode(String email) {
        if(BooleanUtils.isFalse(webConfig.getRegister())) {
            throw new ServiceException("对不起！本站暂未开启注册功能！");
        }
        if (!emailManager.isOk()) {
            throw new ServiceException("对不起！本站邮箱系统未配置，暂不支持注册！");
        }
        email = email.trim();
        boolean isEmail = Validator.isEmail(email);
        if (!isEmail) {
            throw new ServiceException("对不起！您的邮箱格式不正确！");
        }
        String lockKey = Constants.Email.REGISTER_EMAIL_LOCK + email;
        if (redisUtil.hasKey(lockKey)) {
            throw new ServiceException("对不起，您的操作频率过快，请在" + redisUtil.getExpire(lockKey) + "秒后再次发送注册邮件！");
        }
        // 查询邮箱是否已经重复存在
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>().eq("email", email);
        UserInfo userInfo = userInfoService.getOne(queryWrapper, false);
        if (userInfo != null) {
            throw new ServiceException("对不起！该邮箱已被注册，请更换新的邮箱！");
        }
        // 随机生成6位数字的组合
        String numbers = RandomUtil.randomNumbers(6);
        // 缓存key
        redisUtil.set(Constants.Email.REGISTER_KEY_PREFIX.getValue() + email, numbers, 5 * 60);//默认验证码有效5分钟
        // 邮箱发送邮件
        emailManager.sendCode(email, numbers);
        redisUtil.set(lockKey, 0, 60);

        RegisterCodeVO registerCodeVo = new RegisterCodeVO();
        registerCodeVo.setEmail(email);
        registerCodeVo.setExpire(5 * 60);

        return CommonResult.succeedResult(registerCodeVo);
    }

    /**
     * 注册逻辑，具体参数请看RegisterDto类
     * @param registerDto
     * @return
     */
    public CommonResult<?> register(RegisterDTO registerDto) {
        if (!webConfig.getRegister()) { // 需要判断一下网站是否开启注册
            throw new ServiceException("对不起！本站暂未开启注册功能！");
        }
        String codeKey = Constants.Email.REGISTER_KEY_PREFIX.getValue() + registerDto.getEmail();
        if (!redisUtil.hasKey(codeKey)) {
            throw new ServiceException("验证码不存在或已过期");
        }
        if (!redisUtil.get(codeKey).equals(registerDto.getCode())) { //验证码判断
            throw new ServiceException("验证码不正确");
        }
        if (registerDto.getUsername().length() > 20) {
            throw new ServiceException("用户名长度不能超过20位!");
        }
        if (registerDto.getPassword().length() < 6 || registerDto.getPassword().length() > 20) {
            throw new ServiceException("密码长度应该为6~20位！");
        }
        String uuid = IdUtil.simpleUUID();
        //为新用户设置uuid
        registerDto.setUid(uuid);
        registerDto.setPassword(SecureUtil.md5(registerDto.getPassword().trim())); // 将密码MD5加密写入数据库
        registerDto.setUsername(registerDto.getUsername().trim());
        registerDto.setEmail(registerDto.getEmail().trim());

        //往user_auth和 user_info表插入数据
        boolean addUser = userAuthService.addUser(registerDto);
        if (!addUser) {
            throw new ServiceException("注册失败，请稍后重新尝试！");
        }
        redisUtil.delete(registerDto.getEmail());
        //noticeManager.syncNoticeToNewRegisterUser(uuid);
        return CommonResult.succeedResult();
    }

    /**
     * 发送重置密码的链接邮件
     * @param applyResetPasswordDto
     * @return
     */
    public CommonResult<?> applyResetPassword(ApplyResetPasswordDTO applyResetPasswordDto) {
        if (!emailManager.isOk()) {
            throw new ServiceException("对不起！本站邮箱系统未配置，暂不支持重置密码！");
        }

        String captcha = applyResetPasswordDto.getCaptcha();
        String captchaKey = applyResetPasswordDto.getCaptchaKey();
        String email = applyResetPasswordDto.getEmail();

        String lockKey = Constants.Email.RESET_EMAIL_LOCK + email;
        if (redisUtil.hasKey(lockKey)) {
            throw new ServiceException("对不起，您的操作频率过快，请在" + redisUtil.getExpire(lockKey) + "秒后再次发送重置邮件！");
        }

        // 获取redis中的验证码
        String redisCode = (String) redisUtil.get(captchaKey);
        if (!redisCode.equals(captcha.trim().toLowerCase())) {
            throw new ServiceException("验证码不正确");
        }

        // 校验用户邮箱是否存在
        LambdaQueryWrapper<UserAuth> userInfoQueryWrapper = new LambdaQueryWrapper<UserAuth>()
            .eq(UserAuth::getEmail, email.trim());
        UserAuth userInfo = userAuthService.getOne(userInfoQueryWrapper, false);
        if (userInfo == null) {
            throw new ServiceException("对不起，该邮箱无该用户，请重新检查！");
        }

        String code = IdUtil.simpleUUID().substring(0, 21); // 随机生成20位数字与字母的组合
        redisUtil.set(Constants.Email.RESET_PASSWORD_KEY_PREFIX.getValue() + userInfo.getUsername(), code, 10 * 60);//默认链接有效10分钟
        // 发送邮件
        emailManager.sendResetPassword(userInfo.getUsername(), code, email.trim());
        redisUtil.set(lockKey, 0, 90);

        return CommonResult.succeedResult();
    }

    /**
     * 用户重置密码
     * @param resetPasswordDto
     * @return
     */
    public CommonResult<?> resetPassword(ResetPasswordDTO resetPasswordDto) {

        return CommonResult.succeedResult();
    }
}
