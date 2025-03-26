package alekssandher.free_library.modules.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.book.BookResponseAdminDto;
import alekssandher.free_library.dto.response.ApiResponseDto.OkResponse;
import alekssandher.free_library.dto.response.ErrorResponses.Forbidden;
import alekssandher.free_library.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.free_library.dto.response.ErrorResponses.NotFound;
import alekssandher.free_library.dto.user.UserResponseDto;
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
@RequestMapping("admin")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Admin", description = "Endpoints for users/book admin management.")
@ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class)))
})
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service)
    {   
        this.service = service;
    }

    @Operation(summary = "Find users by name", description = "Retrieves a list of users that match the given name.")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = OkResponse.class)))
    @GetMapping("users/{name}")
    public ResponseEntity<OkResponse<List<UserResponseDto>>> findUsersByName(@Valid @PathVariable String name, HttpServletRequest request)
    {
        var result = service.findUsersByName(name);

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse<List<UserResponseDto>>(result, request));
    }

    @Operation(summary = "Get books", description = "Retrieves a list of books based on optional filters: title, author, and category.")
    @ApiResponse(responseCode = "200", description = "Books retrieved successfully", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = OkResponse.class)))
    @GetMapping("books")
    public ResponseEntity<OkResponse<List<BookResponseAdminDto>>> getBooks(
        @Valid @RequestParam(required = false, defaultValue = "") String title,
        @Valid @RequestParam(required = false, defaultValue = "") String author,
        @Valid @RequestParam(required = false, defaultValue = "") String category,
        @Valid @RequestParam(defaultValue = "0") int page,
        @Valid @RequestParam(defaultValue = "10") int size,
        HttpServletRequest request
    )
    {
        var result = service.listBooks(title, author, category, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse<List<BookResponseAdminDto>>(result, request));
    }

    @Operation(summary = "Delete a book", description = "Deletes a book by its public ID.")
    @ApiResponse(responseCode = "204", description = "Book deleted successfully.",
                 content = @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @DeleteMapping("books/{bookPublicId}")
    public ResponseEntity<String> deleteBookByPublicId(@Valid @PathVariable Long bookPublicId)
    {
        service.deleteBook(bookPublicId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change user status", description = "Activates or deactivates a user by their public ID.")
    @ApiResponse(responseCode = "200", description = "User status changed successfully", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OkResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @PatchMapping("users/{userPublicId}/{kind}")
    public ResponseEntity<OkResponse<Void>> inactiveUser(@Valid @PathVariable Long userPublicId, @Valid @PathVariable Boolean kind, HttpServletRequest request)
    {
        service.chageActiveUserStatus(userPublicId, kind);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse<Void>(null, request));
    }
}
