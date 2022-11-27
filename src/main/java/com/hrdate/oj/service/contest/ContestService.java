package com.hrdate.oj.service.contest;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.contest.Contest;
import com.hrdate.oj.mapper.ContestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@Service
public class ContestService extends ServiceImpl<ContestMapper, Contest> {
}
