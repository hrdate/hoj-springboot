package top.hcode.hoj.service.admin.training;

import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.entity.training.TrainingCategory;

/**
 * @Description:
 */
public interface AdminTrainingCategoryService {

    public CommonResult<TrainingCategory> addTrainingCategory(TrainingCategory trainingCategory);

    public CommonResult<Void> updateTrainingCategory(TrainingCategory trainingCategory);

    public CommonResult<Void> deleteTrainingCategory(Long cid);
}