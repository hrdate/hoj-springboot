package top.hcode.hoj.service.file;

import org.springframework.web.multipart.MultipartFile;
import top.hcode.hoj.common.result.CommonResult;

/**
 * @Description:
 */
public interface ImportFpsProblemService {

    public CommonResult<Void> importFPSProblem(MultipartFile file);
}