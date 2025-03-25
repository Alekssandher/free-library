package alekssandher.free_library.interfaces.book;

import java.util.List;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.dto.book.BookResponseDto;

public interface IBookService {

    void createBook(BookRequestDto dto, String jwt);
    
    List<BookResponseDto> listBooks(String title, String author, String category, int page, int size);

    void addFavoriteBook(Long bookPublicId, String jwt);
}