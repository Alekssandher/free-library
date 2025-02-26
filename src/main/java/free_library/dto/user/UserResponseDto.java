package free_library.dto.user;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UserResponseDto {
    private UUID id;
    private String name; 
    private String email;
    private LocalDateTime createdAt; 

    public UserResponseDto() {}

    public UserResponseDto(UUID id, String name, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }
}