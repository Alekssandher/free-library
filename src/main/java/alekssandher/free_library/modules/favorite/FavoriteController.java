package alekssandher.free_library.modules.favorite;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.book.BookResponseDto;
import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.OkResponse;
import alekssandher.free_library.dto.response.ErrorResponses.Forbidden;
import alekssandher.free_library.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.free_library.dto.response.ErrorResponses.MethodNotAllowed;
import alekssandher.free_library.dto.response.ErrorResponses.NotFound;
import alekssandher.free_library.dto.response.ErrorResponses.Unauthorized;
import alekssandher.free_library.interfaces.favorite.IFavoriteService;
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
@RequestMapping("favorites")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Favorite", description = "Endpoint to manage favorites.")
@ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unauthorized.class))),
        @ApiResponse(responseCode = "401", description = "Forbidden",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class))),
        @ApiResponse(responseCode = "405", description = "Mehod Not Allowed",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MethodNotAllowed.class)))
})
public class FavoriteController {
    private final IFavoriteService service;

    public FavoriteController(IFavoriteService service)
    {   
        this.service = service;
    }

    @Operation(summary = "Get all favorite books", description = "Fetch the list of books marked as favorites by the user.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of favorites",
                 content = @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = OkResponse.class)))
    @GetMapping()
    public ResponseEntity<OkResponse<List<BookResponseDto>>> getFavorites(HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");

        List<BookResponseDto> result = service.getFavorites(jwt);

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse<List<BookResponseDto>>(result, request));
    }

    @Operation(summary = "Add a book to favorites", description = "Add a book to the authenticated user's favorites.")
    @ApiResponse(responseCode = "201", description = "Successfully added the book to favorites",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class)))
    @ApiResponse(responseCode = "404", description = "Book not found.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @PostMapping("{bookPublicId}")
    public ResponseEntity<CreatedResponse<Void>> addFavorite(@Valid @PathVariable Long bookPublicId, HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");
        service.addFavoriteBook(bookPublicId, jwt);

        return ResponseEntity.status(HttpStatus.CREATED).body( new CreatedResponse<Void>(request, null));
    }

    @Operation(summary = "Remove a book from favorites", description = "Remove a book from the authenticated user's favorites.")
    @ApiResponse(responseCode = "204", description = "Successfully removed the book from favorites",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Book not found.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @PatchMapping("{bookPublicId}")
    public ResponseEntity<Void> removeFavorite(@Valid @PathVariable Long bookPublicId, HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");
        service.removeFavorite(bookPublicId, jwt);

        return ResponseEntity.noContent().build();
    }
}
