package free_library.service.impl;

import org.springframework.stereotype.Service;

import free_library.dto.book.BookRequestDto;
import free_library.mappers.BookMapper;
import free_library.model.BookModel;
import free_library.repository.BookRepository;
import free_library.service.BookService;

@Service
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    
    public BookServiceImpl(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    @Override
    public Boolean create(BookRequestDto bookRequestDto) {
        BookModel bookModel = BookMapper.toModel(bookRequestDto);

        bookRepository.save(bookModel);

        return true;
    }
    
}
