package top.hcode.hoj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.hcode.hoj.pojo.entity.contest.ContestRegister;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @Description:
 */
@Mapper
@Repository
public interface ContestRegisterMapper extends BaseMapper<ContestRegister> {

}
