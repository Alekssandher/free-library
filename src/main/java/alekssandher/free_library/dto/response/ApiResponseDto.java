package alekssandher.free_library.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;

public class ApiResponseDto {

    public static class CreatedResponse<T> extends ApiResponse<T> {

        @Schema(example = "201")
        private final int status = HttpStatus.CREATED.value();
    
        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#name-201-created")
        private final String type = "https://datatracker.ietf.org/doc/html/rfc9110#name-201-created";
    
        @Schema(example = "Created")
        private final String title = "Created";
    
        @Schema(example = "The request was successful, and the resource was created.")
        private final String detail = "The request was successful, and the resource was created.";
    
        @Schema(example = "/api/books/1354098474946269184")
        private final String instance;
    
        @Schema(example = "null")
        private final T data;
    
        @Schema(example = "2025-03-25T11:23:54.360755")
        private final LocalDateTime timestamp = LocalDateTime.now();
    
        public CreatedResponse(HttpServletRequest request, T data) {
            super(
                HttpStatus.CREATED.value(),
                "https://datatracker.ietf.org/doc/html/rfc9110#name-201-created",
                "Created",
                "The request was successful, and the resource was created.",
                request.getRequestURI(),
                data
            );
            this.instance = request.getRequestURI();
            this.data = data;
        }
    }
    
    public static class GetResponse<T> extends ApiResponse<T> {
        @Schema(example = "200")
        private final int status = HttpStatus.OK.value();

        @Schema(example = "https://datatracker.ietf.org/doc/html/rfc9110#name-200-ok")
        private final String type = "https://datatracker.ietf.org/doc/html/rfc9110#name-200-ok";

        @Schema(example = "Request Successful")
        private final String title = "Request Successful";

        @Schema(example = "Request data retrieved successfully.")
        private final String detail = "Request data retrieved successfully.";

        @Schema(example = "/api/books/")
        private final String instance;

        @Schema(example = "{\"id\": 1354098474946269154, \"title\": \"Hamlet\", \"author\": \"William Shakespeare\", \"description\": \"A tragedy by William Shakespeare about Prince Hamlet's revenge against his uncle, who murdered his father.\", \"language\": \"English\", \"publisher\": \"The Folger Shakespeare Library\", \"category\": \"Tragedy\", \"publishedAt\": 1603, \"fileId\": 1354098359716155392, \"uploadedAt\": \"2025-03-25T11:23:54.360755\"}")
        private final T data;

        @Schema(example = "2024-03-25T14:30:00")
        private final LocalDateTime timestamp = LocalDateTime.now();

        public GetResponse(T data, HttpServletRequest request) {
            super(
                HttpStatus.OK.value(),
                "https://datatracker.ietf.org/doc/html/rfc9110#name-200-ok",
                "Request Successful",
                "Request data retrieved successfully.",
                request.getRequestURI(),
                data
            );
            this.instance = request.getRequestURI();
            this.data = data;
        }
    }
}
