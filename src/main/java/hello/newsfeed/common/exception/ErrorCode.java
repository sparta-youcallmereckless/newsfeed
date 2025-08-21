package hello.newsfeed.common.exception;

public enum ErrorCode {

    BAD_REQUEST(400, "잘못된 요청입니다."),
    NOT_FOUND(404, "페이지를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
