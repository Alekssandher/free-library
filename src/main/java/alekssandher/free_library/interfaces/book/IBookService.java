package alekssandher.free_library.interfaces.book;

import alekssandher.free_library.dto.book.BookRequestDto;

public interface IBookService {

    void createBook(BookRequestDto dto, String jwt);
    
}