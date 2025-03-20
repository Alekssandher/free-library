package alekssandher.free_library.dto.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public record UserResponseDto (
    @JsonSerialize(using = ToStringSerializer.class)
    Long id,
    String name,
    String email,
    Boolean isActive,
    Boolean isVerified,
    LocalDateTime createdAt,
    String role
) {}
