package alekssandher.free_library.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import alekssandher.free_library.dto.user.UserRequestDto;
import alekssandher.free_library.dto.user.UserResponseDto;
import alekssandher.free_library.model.user.UserModel;

@Component
public class UserMapper {

    public UserModel toModel(UserRequestDto dto)
    {
        return new UserModel(
            dto.name().trim(), 
            dto.email().trim().toLowerCase(), 
            dto.password()
        );
    }   

    public UserResponseDto toResponseDto(UserModel model)
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

    public List<UserResponseDto> toListResponseDto(List<UserModel> models)
    {
        var modelsConverted = models.stream().map(this::toResponseDto).collect(Collectors.toList());

        return modelsConverted;
    }
}
