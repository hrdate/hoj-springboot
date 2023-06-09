package top.hcode.hoj.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hcode.hoj.pojo.entity.contest.ContestRecord;

/**
 * <p>
 * 服务类
 * </p>
 *
 *
 * @Description:
 */
public interface ContestRecordEntityService extends IService<ContestRecord> {
    void updateContestRecord(Integer score, Integer status, Long submitId, Integer useTime);
}
