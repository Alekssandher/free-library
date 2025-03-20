package alekssandher.free_library.dto.book;

public record BookRequestDto(
    String title,
    String author,
    String description,
    String category,
    String language,
    String publisher,
    Short publishedAt,
    Long fileId
) {}
