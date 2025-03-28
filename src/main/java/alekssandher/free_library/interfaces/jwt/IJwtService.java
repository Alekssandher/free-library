package alekssandher.free_library.interfaces.jwt;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import alekssandher.free_library.entities.user.UserEntity;

public interface IJwtService 
{

    String generateToken(UserEntity model);

    String getSubjectFromToken(String token);

    List<GrantedAuthority> getAuthoritiesFromToken(String token);
}
