package alekssandher.free_library.dto.book;

public record BookRequestDto(
    String title,
    String author,
    String description,
    String language,
    String publisher,
    Short publishedAt,
    Long fileId
) {}
