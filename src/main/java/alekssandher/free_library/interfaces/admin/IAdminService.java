package alekssandher.free_library.interfaces.admin;

import java.io.IOException;
import java.util.List;

import alekssandher.free_library.dto.book.BookResponseDto;
import alekssandher.free_library.dto.user.UserResponseDto;

public interface IAdminService {
    void deleteBook(Long bookPublicId) throws IOException;

    List<UserResponseDto> findUsersByName(String name);

    void chageActiveUserStatus(Long userPublicId, Boolean kind);

    List<BookResponseDto> listBooks(String title, String author, String category, int page, int size);


}
