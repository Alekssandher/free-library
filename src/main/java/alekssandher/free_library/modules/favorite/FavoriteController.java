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
import alekssandher.free_library.dto.response.ApiResponseDto.DeleteResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.GetResponse;
import alekssandher.free_library.interfaces.favorite.IFavoriteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("favorites")
@SecurityRequirement(name = "Authorization")
public class FavoriteController {
    private final IFavoriteService service;

    public FavoriteController(IFavoriteService service)
    {   
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<GetResponse<List<BookResponseDto>>> getFavorites(HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");

        List<BookResponseDto> result = service.getFavorites(jwt);

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<List<BookResponseDto>>(result, request));
    }
    @PostMapping("{bookPublicId}")
    public ResponseEntity<CreatedResponse<Void>> addFavorite(@PathVariable Long bookPublicId, HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");
        service.addFavoriteBook(bookPublicId, jwt);

        return ResponseEntity.status(HttpStatus.CREATED).body( new CreatedResponse<Void>(request, null));
    }

    @PatchMapping("{bookPublicId}")
    public ResponseEntity<DeleteResponse> removeFavorite(@PathVariable Long bookPublicId, HttpServletRequest request)
    {
        String jwt = request.getHeader("Authorization");
        service.removeFavorite(bookPublicId, jwt);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body( new DeleteResponse(request));
    }
}
