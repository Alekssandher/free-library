package alekssandher.free_library.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorResponses {
    public static class InternalErrorCustom extends ErrorDetails 
    {
        @Schema(example = "500")
        private final int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    
        @Schema(example = "Internal Server Error")
        private final String title = "Internal Error";
    
        @Schema(example = "An unexpected error occurred on our server. Please try again later.")
        private final String detail;
    
        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#status.500")
        private final String type = "https://datatracker.ietf.org/doc/html/rfc9110#status.500";
    
        @Schema(example = "/api/users/123")
        private final String instance;
    
        @Schema(example = "2024-03-25T12:15:00")
        private final LocalDateTime timestamp = LocalDateTime.now();
    
        public InternalErrorCustom(HttpServletRequest request) {
            super(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Error",
                "An unexpected error occurred on our server. Please try again later.",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.500",
                request.getRequestURI()
            );
            this.detail = "An unexpected error occurred on our server. Please try again later.";
            this.instance = request.getRequestURI();
        }
    
        public InternalErrorCustom(HttpServletRequest request, String detail) {
            super(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Error",
                detail, 
                "https://datatracker.ietf.org/doc/html/rfc9110#status.500",
                request.getRequestURI()
            );
            this.detail = detail;
            this.instance = request.getRequestURI();
        }
    }
    
    public static class BadRequest extends ErrorDetails 
    {
        @Schema(example = "400")
        private final int status = HttpStatus.BAD_REQUEST.value();

        @Schema(example = "Bad Request")
        private final String title = "Bad Request";

        @Schema(example = "The 'email' field is required and can't be empty.")
        private final String detail;

        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#status.400")
        private final static String type = "https://datatracker.ietf.org/doc/html/rfc9110#status.400";

        @Schema(example = "/api/auth")
        private final String instance;

        @Schema(example = "2024-03-25T12:00:00")
        private final LocalDateTime timestamp = LocalDateTime.now();

        public BadRequest(HttpServletRequest request, String detail) {
            super(HttpStatus.BAD_REQUEST.value(), "Bad Request", detail, type, request.getRequestURI());
            this.detail = detail;
            this.instance = request.getRequestURI();
        }
    }

    public static class MethodNotAllowed extends ErrorDetails 
    {
        @Schema(example = "405")
        private final int status = HttpStatus.BAD_REQUEST.value();

        @Schema(example = "Method Not Allowed")
        private final String title = "Method Not Allowed";

        @Schema(example = "Request method 'GET' is not supported")
        private final String detail;

        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#status.405")
        private final static String type = "https://datatracker.ietf.org/doc/html/rfc9110#status.405";

        @Schema(example = "/api/auth")
        private final String instance;

        @Schema(example = "2024-03-25T12:00:00")
        private final LocalDateTime timestamp = LocalDateTime.now();

        public MethodNotAllowed(HttpServletRequest request, String detail) {
            super(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed", detail, type, request.getRequestURI());
            this.detail = detail;
            this.instance = request.getRequestURI();
        }
    }
    
    public static class Conflict extends ErrorDetails 
    {
        @Schema(example = "409")
        private final int status = HttpStatus.CONFLICT.value();
    
        @Schema(example = "Conflict")
        private final String title = "Conflict";
    
        @Schema(example = "This operation could not be completed due to a resource conflict.")
        private final String detail;
    
        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#status.409")
        private final String type = "https://datatracker.ietf.org/doc/html/rfc9110#status.409";
    
        @Schema(example = "/api/orders/987")
        private final String instance;
    
        @Schema(example = "2024-03-25T14:30:00")
        private final LocalDateTime timestamp = LocalDateTime.now();
    
        public Conflict(HttpServletRequest request, String detail) {
            super(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                detail != null ? detail : "This operation could not be completed due to a resource conflict.",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.409",
                request.getRequestURI()
            );
            this.detail = detail != null ? detail : "This operation could not be completed due to a resource conflict.";
            this.instance = request.getRequestURI();
        }
    }

    public static class Unauthorized extends ErrorDetails 
    {
        @Schema(example = "401")
        private final int status = HttpStatus.UNAUTHORIZED.value();

        @Schema(example = "Unauthorized")
        private final String title = "Unauthorized";

        @Schema(example = "You do not have permission to access this resource.")
        private final String detail;

        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#status.401")
        private final String type = "https://datatracker.ietf.org/doc/html/rfc9110#status.401";

        @Schema(example = "/api/books/")
        private final String instance;

        @Schema(example = "2024-03-25T12:10:00")
        private final LocalDateTime timestamp = LocalDateTime.now();

        public Unauthorized(HttpServletRequest request) {
            super(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "You do not have permission to access this resource.",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.401",
                request.getRequestURI()
            );
            this.detail = "You do not have permission to access this resource.";
            this.instance = request.getRequestURI();
        }
    }

    public static class Forbidden extends ErrorDetails 
    {
        @Schema(example = "403")
        private final int status = HttpStatus.FORBIDDEN.value();

        @Schema(example = "Forbidden")
        private final String title = "Forbidden";

        @Schema(example = "You do not have permission to access this resource.")
        private final String detail;

        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#status.403")
        private final String type = "https://datatracker.ietf.org/doc/html/rfc9110#status.403";

        @Schema(example = "/api/books/")
        private final String instance;

        @Schema(example = "2024-03-25T12:10:00")
        private final LocalDateTime timestamp = LocalDateTime.now();

        public Forbidden(HttpServletRequest request) {
            super(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "You do not have permission to access this resource.",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.403",
                request.getRequestURI()
            );
            this.detail = "You do not have permission to access this resource.";
            this.instance = request.getRequestURI();
        }
    }

    public static class NotFound extends ErrorDetails 
    {
        @Schema(example = "404")
        private final int status = HttpStatus.NOT_FOUND.value();

        @Schema(example = "Not Found")
        private final String title = "Not Found";

        @Schema(example = "Book not found with ID: 19832103123312.")
        private final String detail;

        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#status.404")
        private final static String type = "https://datatracker.ietf.org/doc/html/rfc9110#status.404";

        @Schema(example = "/api/books/")
        private final String instance;

        @Schema(example = "2024-03-25T12:05:00")
        private final LocalDateTime timestamp = LocalDateTime.now();

        public NotFound(HttpServletRequest request,String detail) {
            super(HttpStatus.NOT_FOUND.value(), "Not Found", detail, type, request.getRequestURI());
            this.detail = detail;
            this.instance = request.getRequestURI();
        }
    }
    
    public class TooManyRequests extends ErrorDetails 
    {
        @Schema(example = "429")
        private final int status = HttpStatus.TOO_MANY_REQUESTS.value();
    
        @Schema(example = "Too Many Requests")
        private final String title = "Too Many Requests";
    
        @Schema(example = "You have exceeded the request limit. Please wait 60 seconds before trying again.")
        private final String detail;
    
        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc6585#section-4")
        private final String type = "https://datatracker.ietf.org/doc/html/rfc6585#section-4";
    
        @Schema(example = "/api/login")
        private final String instance;
    
        @Schema(example = "2024-03-25T14:30:00")
        private final LocalDateTime timestamp = LocalDateTime.now();
    
        public TooManyRequests(HttpServletRequest request, String title, String detail) {
            super(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                title != null ? title : "Too Many Requests",
                detail != null ? detail : "You have exceeded the request limit. Please wait 60 seconds before trying again.",
                "https://datatracker.ietf.org/doc/html/rfc6585#section-4",
                request.getRequestURI()
            );
            this.detail = detail != null ? detail : "You have exceeded the request limit. Please wait 60 seconds before trying again.";
            this.instance = request.getRequestURI();
        }
    }
    
}   