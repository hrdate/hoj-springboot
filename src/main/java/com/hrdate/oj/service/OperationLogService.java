package com.hrdate.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.OperationLogModel;
import com.hrdate.oj.mapper.OperationLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description: 操作日志
 * @author: huangrendi
 * @date: 2022-11-11
 **/

@Slf4j
@Service
public class OperationLogService extends ServiceImpl<OperationLogMapper, OperationLogModel> {
}
