package top.hcode.hoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.hcode.hoj.pojo.entity.contest.ContestRecord;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 */
@Mapper
@Repository
public interface ContestRecordMapper extends BaseMapper<ContestRecord> {

}
