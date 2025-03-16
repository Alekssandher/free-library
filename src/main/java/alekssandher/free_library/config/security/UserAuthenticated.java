package alekssandher.free_library.config.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import alekssandher.free_library.model.user.UserModel;

public class UserAuthenticated implements UserDetails {

    private final UserModel user;

    public UserAuthenticated(UserModel user)
    {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
    
    @Override
    public boolean isEnabled()
    {
        return user.getIsActive();
    }

}
