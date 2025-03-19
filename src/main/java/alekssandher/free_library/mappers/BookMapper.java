package alekssandher.free_library.mappers;

import org.springframework.stereotype.Component;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.dto.book.BookResponseDto;
import alekssandher.free_library.model.book.BookModel;
import alekssandher.free_library.model.user.UserModel;

@Component
public class BookMapper {
    
    public BookModel toBookModel(BookRequestDto dto, UserModel user)
    {
        return new BookModel(
            dto.title(),
            dto.author(),
            dto.description(),
            dto.language(),
            dto.publisher(),
            dto.publishedAt(),
            dto.fileId(),
            user
        );
    }

    public BookResponseDto toDto(BookModel book) {
        return new BookResponseDto(
            book.getPublicId(),
            book.getTitle(),
            book.getAuthor(),
            book.getDescription(),
            book.getLanguage(),
            book.getPublisher(),
            book.getPublishedAt(),
            book.getFileId(),
            book.getUploadedAt(),
            book.getUploadedBy().getPublicId()
        );
    }
}
