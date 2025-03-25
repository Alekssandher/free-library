package alekssandher.free_library.modules.book;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.dto.book.BookResponseDto;
import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.GetResponse;
import alekssandher.free_library.interfaces.book.IBookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("books")
@SecurityRequirement(name = "Authorization")
public class BookController {
    private final IBookService service;

    public BookController(IBookService service)
    {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<GetResponse<List<BookResponseDto>>> getBooks(
        @RequestParam(required = false, defaultValue = "") String title,
        @RequestParam(required = false, defaultValue = "") String author,
        @RequestParam(required = false, defaultValue = "") String category,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        HttpServletRequest request
    )
    {
        var result = service.listBooks(title, author, category, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<List<BookResponseDto>>(result, request));
    }

    @PostMapping()
    public ResponseEntity<CreatedResponse<Void>> createBook(@Valid @RequestBody BookRequestDto dto, HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");
       
        service.createBook(dto, jwt);

        return ResponseEntity.status(HttpStatus.CREATED).body( new CreatedResponse<Void>(request, null));
    }
    
}
