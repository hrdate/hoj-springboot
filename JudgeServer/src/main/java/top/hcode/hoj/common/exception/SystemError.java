package top.hcode.hoj.common.exception;

import lombok.Data;

/**
 * @Description:
 */
@Data
public class SystemError extends Exception {
    private String message;
    private String stdout;
    private String stderr;

    public SystemError(String message, String stdout, String stderr) {
        super(message + " " + stderr);
        this.message = message;
        this.stdout = stdout;
        this.stderr = stderr;
    }

}