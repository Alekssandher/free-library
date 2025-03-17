package alekssandher.free_library.dto.book;

public record BookRequestDto(
    String title,
    String author,
    String description,
    Long fileId,
    Long uploadedBy
) {}
