package free_library.service;

import java.util.List;

import free_library.dto.book.BookRequestDto;
import free_library.dto.book.BookResponseDto;

public interface BookService {
    Boolean create(BookRequestDto bookRequestDto);
    List<BookResponseDto> findAll();
    Boolean delete(String id);
} 