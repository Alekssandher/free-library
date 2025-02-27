package free_library.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import free_library.dto.book.*;
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

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> findAll()
    {
        List<BookResponseDto> books = service.findAll();
    

        if(books != null) return ResponseEntity.ok(books);  
        else return ResponseEntity.notFound().build(); 
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@RequestParam String id)
    {
        Boolean deleted = service.delete(id);

        if(deleted) return ResponseEntity.ok("Deleted");
        else return ResponseEntity.ok("Not found");
    }
}
