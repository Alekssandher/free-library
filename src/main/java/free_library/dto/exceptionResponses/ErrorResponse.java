package free_library.dto.exceptionResponses;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

//Base
public abstract class ErrorResponse {

    private final ZonedDateTime time;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    protected ErrorResponse(int status, String error, String message, String path) {
        this.time = ZonedDateTime.now(ZoneOffset.UTC);
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ZonedDateTime getTime() { return time; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getPath() { return path; }

    //Responses 
    
    public static class InternalErrorResponse extends ErrorResponse {
        public InternalErrorResponse(String path) {
            super(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                  "Internal Server Error", 
                  "Something went wrong at our side.", 
                  path);
        }
    }

    public static class NotFoundResponse extends ErrorResponse {
        public NotFoundResponse(String path) {
            super(HttpStatus.NOT_FOUND.value(), 
                  "Not Found", 
                  "The requested resource could not be found.", 
                  path);
        }
    }

    public static class BadRequestResponse extends ErrorResponse {
        public BadRequestResponse(String path, String message) {
            super(HttpStatus.BAD_REQUEST.value(), 
                  "Bad Request", 
                  message != null ? message : "Request malformed", 
                  path);
        }
    }

    public static class ContentTooLargeResponse extends ErrorResponse {
        public ContentTooLargeResponse(String path) {
            super(HttpStatus.PAYLOAD_TOO_LARGE.value(), 
                  "Request Too Long", 
                  "The request URI is too long.", 
                  path);
        }
    }
}
