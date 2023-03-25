package top.hcode.hoj.dao.group;

import top.hcode.hoj.pojo.entity.training.Training;
import top.hcode.hoj.pojo.vo.TrainingVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description:
 */
public interface GroupTrainingEntityService extends IService<Training> {

    IPage<TrainingVO> getTrainingList(int limit, int currentPage, Long gid);

    IPage<Training> getAdminTrainingList(int limit, int currentPage, Long gid);

}
