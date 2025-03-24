package alekssandher.free_library.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto (
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be at beteween 3 and 100 chars.")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Formato de e-mail inválido")
    @Size(min = 3, max = 100, message = "Email must be at beteween 3 and 100 chars.")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 15, message = "Password must be at beteween 6 and 15 chars.")
    String password

) {}
