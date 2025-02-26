package free_library.mappers;

import free_library.dto.book.BookRequestDto;
import free_library.dto.book.BookResponseDto;
import free_library.model.BookModel;

public class BookMapper {
    public static BookRequestDto toRequestDto( BookModel bookModel )
    {
        return new BookRequestDto(
            bookModel.getTitle(),
            bookModel.getAuthor(),
            bookModel.getPublishedAt(),
            bookModel.getLanguage(),
            bookModel.getSummary(),
            bookModel.getCategory()
        );
    }
    public static BookResponseDto toResponseDto( BookModel bookModel )
    {
        return new BookResponseDto(
            bookModel.getPublicId(),
            bookModel.getTitle(),
            bookModel.getAuthor(),
            bookModel.getPublishedAt(),
            bookModel.getLanguage(),
            bookModel.getSummary(),
            bookModel.getCategory()
        );
    }

    public static BookModel toModel(BookRequestDto bookRequestDto)
    {
        return new BookModel(
            bookRequestDto.getTitle(),
            bookRequestDto.getAuthor(),
            bookRequestDto.getPublishedAt(),
            bookRequestDto.getLanguage(),
            bookRequestDto.getSummary(),
            bookRequestDto.getCategory()
        );
    }
}
