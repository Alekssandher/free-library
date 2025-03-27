package alekssandher.free_library.exception;

import java.time.DateTimeException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import alekssandher.free_library.dto.response.ErrorDetails;
import alekssandher.free_library.dto.response.ErrorResponses.*;
import alekssandher.free_library.exception.Exceptions.*;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(NotFoundException ex, HttpServletRequest request)
    {
        ErrorDetails error = new NotFound(request, ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(BadRequestException ex, HttpServletRequest request)
    {
        ErrorDetails error = new BadRequest(request,  ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(InternalErrorException ex, HttpServletRequest request)
    {
        ErrorDetails error = new InternalErrorCustom(request);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(ForbiddenException ex, HttpServletRequest request)
    {
        ErrorDetails error = new Forbidden(request);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(ConflictException ex, HttpServletRequest request)
    {
        ErrorDetails error = new Conflict(request, ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    // JWT
    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(JWTCreationException ex, HttpServletRequest request)
    {
        ErrorDetails error = new InternalErrorCustom(request, ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(JWTVerificationException ex, HttpServletRequest request)
    {
        ErrorDetails error = new InternalErrorCustom(request, ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    // Jakarta Validation
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) 
    {
        String message = String.format("The param '%s' must be of kind %s.", ex.getName(), ex.getRequiredType().getSimpleName());
        ErrorDetails error = new BadRequest(request,  message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErrorDetails> handleDateTimeException(DateTimeException ex, HttpServletRequest request) {
        ErrorDetails error = new BadRequest(request,  "Invalid data format.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDetails> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request)
    {
        ErrorDetails error = new BadRequest(request, "A required value is missing from path.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleJakartaException(MethodArgumentNotValidException ex, HttpServletRequest request)
    {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                                .stream()
                                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                                .collect(Collectors.toList());

        ErrorDetails error = new BadRequest(request, String.join("; ", errors));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleJsonParseException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = "Invalid JSON format";
        
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            List<String> errors = invalidFormatException.getPath()
                    .stream()
                    .map(ref -> "Invalid value for field '" + ref.getFieldName() + "'. Expected type: " + invalidFormatException.getTargetType().getSimpleName())
                    .collect(Collectors.toList());

            message = String.join("; ", errors);
        }
        
        ErrorDetails error = new BadRequest(request, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDetails> handleWrongMethodException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request)
    {
        var error = new MethodNotAllowed(request, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }   
}
