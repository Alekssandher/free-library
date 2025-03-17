package alekssandher.free_library.dto.book;

import java.time.LocalDateTime;

public record BookResponseDto(
    Long publicId,
    String title,
    String author,
    String description,
    Long fileId,
    LocalDateTime uploadedAt,
    Long uploadedBy
) {}