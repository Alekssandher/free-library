package alekssandher.free_library.modules.rating;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.rating.BookRatingResponseDto;
import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.GetResponse;
import alekssandher.free_library.interfaces.rating.IBookRatingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("bookRating")
@SecurityRequirement(name = "Authorization")
public class BookRatingController {
    private final IBookRatingService service;

    public BookRatingController(IBookRatingService service)
    {
        this.service = service;
    }

    @PostMapping("{bookPublicId}/{rating}")
    public ResponseEntity<CreatedResponse<Void>> rateBook(@RequestParam Long bookPublicId, @RequestParam int rating, HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");

        service.rateBook(bookPublicId, rating, jwt);

        return ResponseEntity.status(HttpStatus.CREATED).body( new CreatedResponse<Void>(request, null));
    }

    @GetMapping("{bookPublicId}")
    public ResponseEntity<GetResponse<BookRatingResponseDto>> getRatings(@PathVariable Long bookPublicId, HttpServletRequest request)
    {
        var result = service.getRatings(bookPublicId);

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<BookRatingResponseDto>(result, request));
    }
}
