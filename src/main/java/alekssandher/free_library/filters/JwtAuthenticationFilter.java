package alekssandher.free_library.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import alekssandher.free_library.exception.Exceptions.BadRequestException;
import alekssandher.free_library.modules.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException  
    {
        String token = extractToken(request);
        if (token != null) {
            try {
                String email = jwtService.getSubjectFromToken(token);

                List<GrantedAuthority> authorities = jwtService.getAuthoritiesFromToken(token);
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(email, null, authorities);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } 
            catch (BadRequestException ex) {
                sendErrorResponse(response, HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request.getRequestURI());
                return;
            } 
            catch (JWTVerificationException ex) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid Token", "The token is invalid or expired.", request.getRequestURI());
                return;
            }
            catch (Exception ex) { 
                sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Server Error", "An unexpected error occurred.", request.getRequestURI());
                return;
            }
            
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/");
    }
    
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); 
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String title, String detail, String path) throws IOException {
        String json = String.format(
            "{ \"timestamp\": \"%s\", \"status\": %d, \"title\": \"%s\", \"detail\": \"%s\", \"type\": \"https://datatracker.ietf.org/doc/html/rfc9110#status.%d\",\"path\": \"%s\" }",
            java.time.LocalDateTime.now(),
            status.value(),
            title,
            detail,
            status.value(),
            path
        );

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}