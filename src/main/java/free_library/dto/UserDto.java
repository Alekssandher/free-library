package free_library.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
    private String name; 
    private String email;   
    private String password;
    private LocalDateTime createdAt; 
}
