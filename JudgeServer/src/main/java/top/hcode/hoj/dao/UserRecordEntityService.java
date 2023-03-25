package top.hcode.hoj.dao;


import com.baomidou.mybatisplus.extension.service.IService;
import top.hcode.hoj.pojo.entity.user.UserRecord;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @Description:
 */
public interface UserRecordEntityService extends IService<UserRecord> {
    void updateRecord(String uid, Long submitId, Long pid, Integer score);
}
