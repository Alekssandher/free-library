package alekssandher.free_library.config.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import alekssandher.free_library.repository.IUserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    private IUserRepository repository;

    public UserDetailsServiceImpl(IUserRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        var model = repository.findByEmail(email);
        
        if(model == null) throw new UsernameNotFoundException("User not found");

        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + model.getRole()));

        return new User(
            model.getEmail(),          
            model.getPassword(),        
            authorities 
        );
    }
    
}
