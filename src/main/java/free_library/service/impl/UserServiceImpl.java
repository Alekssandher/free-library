package free_library.service.impl;

import org.springframework.stereotype.Service;

import free_library.dto.UserDto;
import free_library.repository.UserRepository;
import free_library.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findByEmail(String email) {
        System.err.println("reached implementation");
        return userRepository.findByEmail(email).orElse(null);
    }
    
}
