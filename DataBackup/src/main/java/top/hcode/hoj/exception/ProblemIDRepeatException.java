package top.hcode.hoj.exception;

/**
 * @Description:
 */
public class ProblemIDRepeatException extends RuntimeException{
    public ProblemIDRepeatException() {
        super();
    }

    public ProblemIDRepeatException(String message) {
        super(message);
    }

    public ProblemIDRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProblemIDRepeatException(Throwable cause) {
        super(cause);
    }

    protected ProblemIDRepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
