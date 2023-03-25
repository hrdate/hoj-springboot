package top.hcode.hoj.dao.training;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hcode.hoj.pojo.entity.training.TrainingRecord;
import top.hcode.hoj.pojo.vo.TrainingRecordVO;

import java.util.List;


/**
 * @Description:
 */
public interface TrainingRecordEntityService extends IService<TrainingRecord> {

    public List<TrainingRecordVO> getTrainingRecord(Long tid);

}