package alekssandher.free_library.modules.admin;

import java.io.IOException;
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
import alekssandher.free_library.dto.user.UserResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin")
@SecurityRequirement(name = "Authorization")
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service)
    {   
        this.service = service;
    }

    @GetMapping("users/{name}")
    public ResponseEntity<GetResponse<List<UserResponseDto>>> findUsersByName(@PathVariable String name, HttpServletRequest request)
    {
        var result = service.findUsersByName(name);

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<List<UserResponseDto>>(result, request));
    }

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

    @DeleteMapping("books/{bookPublicId}")
    public ResponseEntity<String> deleteBookByPublicId(@PathVariable Long bookPublicId)
    {
        try {
            service.deleteBook(bookPublicId);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.ok("Deleted");
    }

    @PatchMapping("users/{userPublicId}/{kind}")
    public ResponseEntity<String> inactiveUser(@PathVariable Long userPublicId, @PathVariable Boolean kind)
    {
        service.chageActiveUserStatus(userPublicId, kind);

        return ResponseEntity.ok("Inactived");
    }
}
