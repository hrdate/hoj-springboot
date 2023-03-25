package top.hcode.hoj.service.admin.account;


import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.dto.LoginDTO;
import top.hcode.hoj.pojo.vo.UserInfoVO;

/**
 * @Description:
 */
public interface AdminAccountService {

    public CommonResult<UserInfoVO> login(LoginDTO loginDto);

    public CommonResult<Void> logout();
}