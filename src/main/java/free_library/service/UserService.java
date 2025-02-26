package free_library.service;

import free_library.dto.user.*;

public interface UserService{
    UserResponseDto create(UserDto user);
    UserResponseDto findByEmail(String email);
}
