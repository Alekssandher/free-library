package alekssandher.free_library.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import alekssandher.free_library.dto.user.UserRequestDto;
import alekssandher.free_library.dto.user.UserResponseDto;
import alekssandher.free_library.entities.user.UserEntity;

@Component
public class UserMapper {

    public UserEntity toUserEntity(UserRequestDto dto)
    {
        return new UserEntity(
            dto.name().trim(), 
            dto.email().trim().toLowerCase(), 
            dto.password()
        );
    }   

    public UserResponseDto toUserResponseDto(UserEntity model)
    {   
        return new UserResponseDto(
            model.getPublicId(),
            model.getName(), 
            model.getEmail(), 
            model.getIsActive(), 
            model.getIsVerified(), 
            model.getCreatedAt(), 
            model.getRole()
        );
    }

    public List<UserResponseDto> toListResponseDto(List<UserEntity> models)
    {
        var modelsConverted = models.stream().map(this::toUserResponseDto).collect(Collectors.toList());

        return modelsConverted;
    }
}
