package top.hcode.hoj.service.admin.system;

import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.entity.user.Session;

import java.util.Map;

/**
 * @Description:
 */
public interface DashboardService {

    public CommonResult<Session> getRecentSession();

    public CommonResult<Map<Object,Object>> getDashboardInfo();
}