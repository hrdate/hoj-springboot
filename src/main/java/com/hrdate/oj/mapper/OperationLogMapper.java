package com.hrdate.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrdate.oj.entity.OperationLogModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 操作日志
 * @author: huangrendi
 * @date: 2022-11-11
 **/

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLogModel> {
}
