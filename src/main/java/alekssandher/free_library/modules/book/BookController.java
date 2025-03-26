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
import alekssandher.free_library.dto.response.ErrorResponses.BadRequest;
import alekssandher.free_library.dto.response.ErrorResponses.Forbidden;
import alekssandher.free_library.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.free_library.interfaces.book.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("books")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Books", description = "Endpoint create/get books.")
@ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class)))
})
public class BookController {
    private final IBookService service;

    public BookController(IBookService service)
    {
        this.service = service;
    }

    @Operation(summary = "Find books", description = "Retrieves a list of books that match the given query.")
    @ApiResponse(responseCode = "200", description = "Books retrieved successfully", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class)))
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
    @Operation(summary = "Create book", description = "Create a book with the infos given and id of file uploaded in /api/pdfs route.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Book successfully created.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class))),
        @ApiResponse(responseCode = "400", description = "Malformed request.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class)))
    })
    @PostMapping()
    public ResponseEntity<CreatedResponse<Void>> createBook(@Valid @RequestBody BookRequestDto dto, HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");
       
        service.createBook(dto, jwt);

        return ResponseEntity.status(HttpStatus.CREATED).body( new CreatedResponse<Void>(request, null));
    }
    
}
