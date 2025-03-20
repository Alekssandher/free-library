package alekssandher.free_library.dto.book;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public record BookResponseDto(
    @JsonSerialize(using = ToStringSerializer.class)
    Long id,
    String title,
    String author,
    String description,
    String language,
    String publisher,
    String category,
    Short publishedAt,
    @JsonSerialize(using = ToStringSerializer.class)
    Long fileId,
    LocalDateTime uploadedAt,
    @JsonSerialize(using = ToStringSerializer.class)
    Long uploadedBy
) {}