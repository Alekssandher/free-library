package alekssandher.free_library.modules.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.interfaces.book.IBookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("books")
@SecurityRequirement(name = "Authorization")
public class BookController {
    private final IBookService service;

    public BookController(IBookService service)
    {
        this.service = service;
    }

    @PostMapping("createBook")
    public ResponseEntity<CreatedResponse<Void>> createBook(@RequestBody BookRequestDto dto, @RequestHeader("Authorization") String jwt, HttpServletRequest request)
    {
        service.createBook(dto, jwt);

        return ResponseEntity.status(HttpStatus.CREATED).body( new CreatedResponse<Void>(request, null));
    }

}
