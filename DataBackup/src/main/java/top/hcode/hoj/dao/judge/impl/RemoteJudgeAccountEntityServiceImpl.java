package top.hcode.hoj.dao.judge.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.mapper.RemoteJudgeAccountMapper;
import top.hcode.hoj.pojo.entity.judge.RemoteJudgeAccount;
import top.hcode.hoj.dao.judge.RemoteJudgeAccountEntityService;


/**
 * @Description:
 */
@Service
public class RemoteJudgeAccountEntityServiceImpl extends ServiceImpl<RemoteJudgeAccountMapper, RemoteJudgeAccount> implements RemoteJudgeAccountEntityService {
}