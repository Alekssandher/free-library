package alekssandher.free_library.mappers;

import org.springframework.stereotype.Component;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.dto.book.BookResponseAdminDto;
import alekssandher.free_library.dto.book.BookResponseDto;
import alekssandher.free_library.entities.book.BookEntity;
import alekssandher.free_library.entities.user.UserEntity;

@Component
public class BookMapper {
    
    public BookEntity toBookEntity(BookRequestDto dto, UserEntity user)
    {
        return new BookEntity(
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

    public BookResponseDto toResponseDto(BookEntity book) {
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

    public BookResponseAdminDto toResponseAdminDto(BookEntity book) {
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
