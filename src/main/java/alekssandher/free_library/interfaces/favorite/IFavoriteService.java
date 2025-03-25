package alekssandher.free_library.interfaces.favorite;

import java.util.List;

import alekssandher.free_library.dto.book.BookResponseDto;

public interface IFavoriteService {

    void addFavoriteBook(Long bookPublicId, String jwt);

    void removeFavorite(Long bookPublicId, String jwt);

    List<BookResponseDto> getFavorites(String jwt);

}
