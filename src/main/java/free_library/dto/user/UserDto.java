package free_library.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private String name; 
    private String email;   
    private String password;

    public UserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
