package alekssandher.free_library.modules.rating;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.rating.BookRatingResponseDto;
import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.GetResponse;
import alekssandher.free_library.dto.response.ErrorResponses.BadRequest;
import alekssandher.free_library.dto.response.ErrorResponses.Forbidden;
import alekssandher.free_library.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.free_library.dto.response.ErrorResponses.NotFound;
import alekssandher.free_library.interfaces.rating.IBookRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("bookRating")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Book Rating", description = "Endpoints to manage books")
@ApiResponses({
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))
})
public class BookRatingController {
    private final IBookRatingService service;

    public BookRatingController(IBookRatingService service)
    {
        this.service = service;
    }

    @Operation(summary = "Rate a book", description = "Allows a user to rate a book by providing a rating score.")
    @ApiResponse(responseCode = "201", description = "Book rated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid rating value",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class)))
    @ApiResponse(responseCode = "403", description = "Unauthorized",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class)))
    @PostMapping("{bookPublicId}/{rating}")
    public ResponseEntity<CreatedResponse<Void>> rateBook(@PathVariable Long bookPublicId, @PathVariable int rating, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        service.rateBook(bookPublicId, rating, jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse<>(request, null));
    }

    @Operation(summary = "Get book ratings", description = "Retrieves the average rating and count of ratings for a given book.")
    @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class)))
    @ApiResponse(responseCode = "404", description = "Book not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @GetMapping("{bookPublicId}")
    public ResponseEntity<GetResponse<BookRatingResponseDto>> getRatings(@PathVariable Long bookPublicId, HttpServletRequest request) {
        var result = service.getRatings(bookPublicId);
        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<>(result, request));
    }
}
