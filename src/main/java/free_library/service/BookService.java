package free_library.service;

import free_library.dto.book.BookRequestDto;

public interface BookService {
    Boolean create(BookRequestDto bookRequestDto);
} 