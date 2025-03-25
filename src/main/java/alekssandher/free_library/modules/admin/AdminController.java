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
import alekssandher.free_library.dto.response.ApiResponseDto.GetResponse;
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

@RestController
@RequestMapping("admin")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Admin", description = "Endpoints for users/book admin management.")
@ApiResponses({
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))
})
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service)
    {   
        this.service = service;
    }

    @Operation(summary = "Find users by name", description = "Retrieves a list of users that match the given name.")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class)))
    @GetMapping("users/{name}")
    public ResponseEntity<GetResponse<List<UserResponseDto>>> findUsersByName(@PathVariable String name, HttpServletRequest request)
    {
        var result = service.findUsersByName(name);

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<List<UserResponseDto>>(result, request));
    }

    @Operation(summary = "Get books", description = "Retrieves a list of books based on optional filters: title, author, and category.")
    @ApiResponse(responseCode = "200", description = "Books retrieved successfully", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class)))
    @GetMapping("books")
    public ResponseEntity<GetResponse<List<BookResponseAdminDto>>> getBooks(
        @RequestParam(required = false, defaultValue = "") String title,
        @RequestParam(required = false, defaultValue = "") String author,
        @RequestParam(required = false, defaultValue = "") String category,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        HttpServletRequest request
    )
    {
        var result = service.listBooks(title, author, category, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<List<BookResponseAdminDto>>(result, request));
    }

    @Operation(summary = "Delete a book", description = "Deletes a book by its public ID.")
    @ApiResponse(responseCode = "200", description = "Book deleted successfully")
    @ApiResponse(responseCode = "404", description = "Book not found", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @DeleteMapping("books/{bookPublicId}")
    public ResponseEntity<String> deleteBookByPublicId(@PathVariable Long bookPublicId)
    {
        service.deleteBook(bookPublicId);
        return ResponseEntity.ok("Deleted");
    }

    @Operation(summary = "Change user status", description = "Activates or deactivates a user by their public ID.")
    @ApiResponse(responseCode = "200", description = "User status changed successfully")
    @ApiResponse(responseCode = "404", description = "User not found", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @PatchMapping("users/{userPublicId}/{kind}")
    public ResponseEntity<String> inactiveUser(@PathVariable Long userPublicId, @PathVariable Boolean kind)
    {
        service.chageActiveUserStatus(userPublicId, kind);
        return ResponseEntity.ok("Inactived");
    }
}
