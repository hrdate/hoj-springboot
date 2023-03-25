package top.hcode.hoj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.hcode.hoj.pojo.entity.problem.Problem;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 *
 * @Description:
 */
@Mapper
@Repository
public interface ProblemMapper extends BaseMapper<Problem> {

}
