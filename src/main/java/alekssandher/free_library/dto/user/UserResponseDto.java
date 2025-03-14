package alekssandher.free_library.dto.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseDto (
    @JsonProperty("id")
    long id,
    String name,
    String email,
    boolean isActive,
    boolean isVerified,
    LocalDateTime createdAt,
    String role
) {}
