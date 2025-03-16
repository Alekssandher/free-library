package alekssandher.free_library.modules.jwt;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import alekssandher.free_library.model.user.UserModel;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class JwtService {

    private final Dotenv dotenv;
    private final String secret;
    private final String emissor;
    private final JWTVerifier jwtVerifier;
    
    public JwtService(Dotenv dotenv)
    {
        this.dotenv = dotenv;
        this.secret = this.dotenv.get("SECRET");
        this.emissor = this.dotenv.get("EMISSOR");
        
        if (this.secret == null || this.emissor == null) {
            throw new IllegalStateException("SECRET e EMISSOR devem estar definidos no arquivo .env");
        }

        this.jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
    }

    public String generateToken(UserModel model)
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
            throw new JWTCreationException("Erro ao gerar token.", exception);
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

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Erro ao verificar token.", exception);
        }
    }
    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        
        // Assume que as roles estão armazenadas como uma lista no claim "roles"
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        
        if (roles == null) return Collections.emptyList();

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Instant creationDate() {
        return Instant.now();
    }

    private Instant expirationDate() {
        return Instant.now().plusSeconds(14400);
    }
}
