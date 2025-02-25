package free_library.service;

import free_library.dto.UserDto;

public interface UserService{
    UserDto findByEmail(String email);
}
