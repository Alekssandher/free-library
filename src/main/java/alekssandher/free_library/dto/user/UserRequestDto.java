package alekssandher.free_library.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto (
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    String name,

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Size(min = 3, max = 100, message = "O email deve ter entre 3 e 100 caracteres")
    String email,

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 15, message = "A senha deve ter entre 6 e 15 caracteres")
    String password

) {}
