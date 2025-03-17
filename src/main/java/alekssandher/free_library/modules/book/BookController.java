package alekssandher.free_library.modules.book;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.interfaces.book.IBookService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @PostMapping("uploadBook")
    public ResponseEntity<CreatedResponse<Void>> uploadBook(@RequestBody final BookRequestDto dto, @RequestHeader("Authorization") String jwt)
    {
        service.uploadBook(dto, jwt);

        return null;
    }

    @PostMapping("uploadPdf")
    public ResponseEntity<CreatedResponse<String>> uploadPdf(
        @RequestParam("pdf") 
        @RequestBody(
            description = "Arquivo PDF a ser enviado",
            required = true,
            content = @Content(
                mediaType = "multipart/form-data",
                schema = @Schema(type = "string", format = "binary")
            )
        )
        MultipartFile pdf, 
        
        HttpServletRequest request
        ) throws IOException
    {
        var result = service.uploadPdf(pdf);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse<String>(request, result));
    }
}
