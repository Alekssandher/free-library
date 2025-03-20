package alekssandher.free_library.interfaces.admin;

import java.io.IOException;
import java.util.List;

import alekssandher.free_library.dto.book.BookResponseAdminDto;
import alekssandher.free_library.dto.user.UserResponseDto;

public interface IAdminService {
    void deleteBook(Long bookPublicId) throws IOException;

    List<UserResponseDto> findUsersByName(String name);

    void chageActiveUserStatus(Long userPublicId, Boolean kind);

    List<BookResponseAdminDto> listBooks(String title, String author, String category, int page, int size);


}
