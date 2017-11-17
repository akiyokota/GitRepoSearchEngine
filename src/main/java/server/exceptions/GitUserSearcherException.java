package server.exceptions;

import server.enums.GitUserSearcherCode;

/**
 * Created by ayokota on 11/16/17.
 */
public class GitUserSearcherException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected GitUserSearcherCode errorCode;

    public GitUserSearcherCode getErrorCode() {
        return errorCode;
    }

    public GitUserSearcherException(String errorMessage) {
        super(errorMessage);
        errorCode = GitUserSearcherCode.UNKNOWN_ERROR;
    }

    public GitUserSearcherException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        errorCode = GitUserSearcherCode.GENERAL_ERROR;
    }

    public GitUserSearcherException(String errorMessage, GitUserSearcherCode errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public GitUserSearcherException(String errorMessage, GitUserSearcherCode errorCode, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
    }
}
