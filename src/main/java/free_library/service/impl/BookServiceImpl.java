package free_library.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import free_library.dto.book.BookRequestDto;
import free_library.dto.book.BookResponseDto;
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

    @Override 
    public List<BookResponseDto> findAll()
    {
        List<BookModel> booksModel = bookRepository.findAll();

        List<BookResponseDto> books = booksModel.stream().map(BookMapper::toResponseDto).collect(Collectors.toList());

        return books;
    }

    @Override
    public Boolean delete(String id)
    {
        Optional<BookModel> book = bookRepository.findByPublicId(id);
        
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return true; 
        }

        return false;
    }
    
}
