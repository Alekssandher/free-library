package alekssandher.free_library.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException 
        {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        String jsonResponse = String.format(
            "{ \"timestamp\": \"%s\", \"status\": %d, \"title\": \"Forbidden\", \"detail\": \"You do not have permission to access this resource\", \"type\": \"https://datatracker.ietf.org/doc/html/rfc9110#status.403\",\"path\": \"%s\" }",
            java.time.LocalDateTime.now(),
            HttpServletResponse.SC_FORBIDDEN,
            request.getRequestURI()
        );

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
