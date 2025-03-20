package alekssandher.free_library.model.user;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import alekssandher.free_library.config.SnowFlakeSing;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "USERS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_EMAIL", columnNames = "email")
    }
)
@Getter
@Setter
@ToString(exclude = "password")
public class UserModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "public_id", unique = true, nullable = false)
    private Long publicId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 150)
    private String password;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Boolean isVerified = false;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, length = 10) 
    private String role = "USER";

    public UserModel() {}

    public UserModel(String name, String email, String hashedPassword) {
        this.name = name;
        this.email = email;
        this.password = hashedPassword;
        this.isActive = true;
        this.isVerified = false;
        this.createdAt = LocalDateTime.now();
        this.role = "USER";
        this.publicId = SnowFlakeSing.getInstance().nextId();
    }

}

