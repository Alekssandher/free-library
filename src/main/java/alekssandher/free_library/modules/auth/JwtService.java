package alekssandher.free_library.modules.auth;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import alekssandher.free_library.entities.user.UserEntity;
import alekssandher.free_library.exception.Exceptions.BadRequestException;

@Service
public class JwtService {

    private final String secret;
    private final String emissor;
    private final JWTVerifier jwtVerifier;

    
    public JwtService( @Value("${SECRET:NOT_SET}") String secret, @Value("${EMISSOR:NOT_SET}") String emissor ) 
    {
        this.secret = secret;
        this.emissor = emissor;
        this.jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
    }

    public String generateToken(UserEntity model)
    {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer(emissor)
                .withIssuedAt(creationDate())
                .withExpiresAt(expirationDate())
                .withSubject(model.getEmail())
                .withClaim("scope", model.getRole())
                .sign(algorithm);

        }catch (JWTCreationException exception) {
            throw new JWTCreationException("Error generating token.", exception);
        }
    }
    
    public String getSubjectFromToken(String token)
    {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(emissor) 
                    .build()
                    .verify(token)
                    .getSubject();
            
        } catch (TokenExpiredException ex) { 
            throw new BadRequestException("Token expired.");

        } catch (SignatureVerificationException ex) { 
            throw new BadRequestException("Invalid token signature.");

        } catch (JWTDecodeException ex) { 
            throw new BadRequestException("Malformed token.");

        } catch (JWTVerificationException ex) { 
            throw ex;
        }
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        
        String role = decodedJWT.getClaim("scope").asString();
    
        if (role == null) return Collections.emptyList();
    
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    private Instant creationDate() {
        return Instant.now();
    }

    private Instant expirationDate() {
        return Instant.now().plusSeconds(14400);
    }
}
