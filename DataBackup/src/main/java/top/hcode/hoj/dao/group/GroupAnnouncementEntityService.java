package top.hcode.hoj.dao.group;

import top.hcode.hoj.pojo.entity.common.Announcement;
import top.hcode.hoj.pojo.vo.AnnouncementVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description:
 */
public interface GroupAnnouncementEntityService extends IService<Announcement> {

    IPage<AnnouncementVO> getAnnouncementList(int limit, int currentPage, Long gid);

    IPage<AnnouncementVO> getAdminAnnouncementList(int limit, int currentPage, Long gid);

}
