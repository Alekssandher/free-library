package free_library.mappers;

import free_library.dto.user.*;
import free_library.model.UserModel;

public class UserMapper {
    public static UserDto toDto(UserModel user)
    {
        return new UserDto(
            user.getName(), 
            user.getEmail(), 
            user.getPassword()
        );
    }
    public static UserResponseDto toResponseDto(UserModel user)
    {
        return new UserResponseDto(
            user.getPublicId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }

    public static UserModel toModel(UserDto user)
    {
        return new UserModel(
            user.getName(), 
            user.getEmail(), 
            user.getPassword()
        );
    }
}
