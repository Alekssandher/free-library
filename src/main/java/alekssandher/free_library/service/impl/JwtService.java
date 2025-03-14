package alekssandher.free_library.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import alekssandher.free_library.model.user.UserModel;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class JwtService {

    private final Dotenv dotenv;
    private final String secret;
    private final String emissor;

    public JwtService(Dotenv dotenv)
    {
        this.dotenv = dotenv;
        this.secret = this.dotenv.get("SECRET");
        this.emissor = this.dotenv.get("EMISSOR");

        if (this.secret == null || this.emissor == null) {
            throw new IllegalStateException("SECRET e EMISSOR devem estar definidos no arquivo .env");
        }
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
    private Instant creationDate() {
        return Instant.now();
    }

    private Instant expirationDate() {
        return Instant.now().plusSeconds(14400);
    }
}
