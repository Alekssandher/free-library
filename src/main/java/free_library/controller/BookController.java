package free_library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import free_library.dto.book.BookRequestDto;
import free_library.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
    
    private BookService service;

    public BookController(BookService service)
    {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<String> create( @RequestBody BookRequestDto bookrRequestDto )
    {
        Boolean created = service.create(bookrRequestDto);

        if(created) return ResponseEntity.ok("Created");
        
        return null;
    } 
}
