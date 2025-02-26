package free_library.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private UUID publicId = UUID.randomUUID(); 
    
    private String name;
    private String email;
    
    @JsonIgnore
    private String password;
    
    private LocalDateTime createdAt = LocalDateTime.now();

    public UserModel(String name, String email, String password) {
        this.publicId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
}
