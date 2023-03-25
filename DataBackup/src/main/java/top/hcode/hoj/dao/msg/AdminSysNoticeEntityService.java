package top.hcode.hoj.dao.msg;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.hcode.hoj.pojo.entity.msg.AdminSysNotice;
import top.hcode.hoj.pojo.vo.AdminSysNoticeVO;


/**
 * @Description:
 */
public interface AdminSysNoticeEntityService extends IService<AdminSysNotice> {

    public IPage<AdminSysNoticeVO> getSysNotice(int limit, int currentPage, String type);

}