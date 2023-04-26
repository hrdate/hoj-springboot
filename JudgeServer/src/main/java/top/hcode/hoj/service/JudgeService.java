package top.hcode.hoj.service;

import top.hcode.hoj.common.exception.SystemError;
import top.hcode.hoj.pojo.dto.TestJudgeReq;
import top.hcode.hoj.pojo.dto.TestJudgeRes;
import top.hcode.hoj.pojo.entity.judge.Judge;
import top.hcode.hoj.pojo.dto.ToJudgeDTO;

import java.util.HashMap;

public interface JudgeService {

    public void judge(Judge judge);

    public TestJudgeRes testJudge(TestJudgeReq testJudgeReq);

    public void remoteJudge(ToJudgeDTO toJudgeDTO);

}
