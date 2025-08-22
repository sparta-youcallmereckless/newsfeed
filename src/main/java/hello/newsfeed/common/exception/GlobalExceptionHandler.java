package hello.newsfeed.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler {

    // JSON 통일 에러 응답 DTO
    static class ErrorResponse {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        public ErrorResponse(ErrorCode errorCode, String path) {
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
            this.status = errorCode.getStatus();
            this.error = getReasonPhrase(errorCode.getStatus());
            this.message = errorCode.getMessage();
            this.path = path;
        }

        private String getReasonPhrase(int status) {
            return switch (status) {
                case 400 -> "Bad Request";
                case 404 -> "Not Found";
                case 500 -> "Internal Server Error";
                case 409 -> "CONFLICT";
                default -> "Unknown Error";
            };
        }

        public String getTimestamp() {
            return timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public String getPath() {
            return path;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e, WebRequest request) {

        String path = request.getDescription(false).replace("uri=", "");

        String message = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "Validation error";

        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST, path);
        errorResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }


    // IllegalArgumentException 처리 → 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException e, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST, path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 모든 예외 처리 → 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, path);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
