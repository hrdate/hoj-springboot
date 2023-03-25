package top.hcode.hoj.common.exception;

import lombok.Data;

/**
 * @Description:
 */
@Data
public class SubmitError extends Exception {
    private String message;
    private String stdout;
    private String stderr;

    public SubmitError(String message, String stdout, String stderr) {
        super(message);
        this.message = message;
        this.stdout = stdout;
        this.stderr = stderr;
    }
}