package alekssandher.free_library.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

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
        ErrorDetails error = new NotFound(request, "Not Found", ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(BadRequestException ex, HttpServletRequest request)
    {
        ErrorDetails error = new BadRequest(request, "Bad Request", ex.getMessage());
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
        ErrorDetails error = new Forbidden(request, ex.getMessage());
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
}
