package free_library.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import free_library.dto.user.*;
import free_library.mappers.UserMapper;
import free_library.model.UserModel;
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
    public UserResponseDto create(UserDto user) {
        if( userRepository.findByEmail(user.getEmail()) == null)
        {
            throw new IllegalArgumentException("This account number already exists");
        }

        UserModel userModel = UserMapper.toModel(user);
        
        userRepository.save(userModel);
    
        return UserMapper.toResponseDto(userModel);
    }

    @Override
    public UserResponseDto findByEmail(String email) {

        Optional<UserModel> user = userRepository.findByEmail(email);
        System.out.println(user);
        return user.map(UserMapper::toResponseDto)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    
}
