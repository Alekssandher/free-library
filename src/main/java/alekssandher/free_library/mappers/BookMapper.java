package alekssandher.free_library.mappers;

import org.springframework.stereotype.Component;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.dto.book.BookResponseAdminDto;
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
            dto.category(),
            dto.publishedAt(),
            dto.fileId(),
            user
        );
    }

    public BookResponseDto toResponseDto(BookModel book) {
        return new BookResponseDto(
            book.getPublicId(),
            book.getTitle(),
            book.getAuthor(),
            book.getDescription(),
            book.getLanguage(),
            book.getPublisher(),
            book.getCategory(),
            book.getPublishedAt(),
            book.getFileId(),
            book.getUploadedAt()
        );
    }

    public BookResponseAdminDto toResponseAdminDto(BookModel book) {
        return new BookResponseAdminDto(
            book.getPublicId(),
            book.getTitle(),
            book.getAuthor(),
            book.getDescription(),
            book.getLanguage(),
            book.getPublisher(),
            book.getCategory(),
            book.getPublishedAt(),
            book.getFileId(),
            book.getUploadedAt(),
            book.getUploadedBy().getPublicId()
        );
    }
}
