package alekssandher.free_library.modules.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import alekssandher.free_library.dto.book.BookResponseAdminDto;
import alekssandher.free_library.dto.user.UserResponseDto;
import alekssandher.free_library.interfaces.admin.IAdminService;
import alekssandher.free_library.mappers.BookMapper;
import alekssandher.free_library.mappers.UserMapper;
import alekssandher.free_library.model.book.BookModel;
import alekssandher.free_library.model.user.UserModel;
import alekssandher.free_library.repository.IBookRepository;
import alekssandher.free_library.repository.IUserRepository;

@Service
public class AdminService implements IAdminService {

    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;

    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final Cloudinary cloudinary;
    
    public AdminService(
        IUserRepository userRepository,
        IBookRepository bookRepository,
        UserMapper userMapper,
        BookMapper bookMapper,
        Cloudinary cloudinary
        )
    {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.cloudinary = cloudinary;
    }

    @Override
    public void deleteBook(Long bookPublicId) throws IOException {
        BookModel book = bookRepository.findByPublicId(bookPublicId).orElseThrow(() -> new RuntimeException("Book not found"));
        cloudinary.uploader().destroy(book.getFileId().toString(), ObjectUtils.asMap("resource_type", "raw"));
        bookRepository.delete(book);
        return;
    }

    @Override
    public List<UserResponseDto> findUsersByName(String name) {
        var users = userRepository.findTop10ByNameContainingIgnoreCase(name);

        return userMapper.toListResponseDto(users);
    }

    @Override
    public void chageActiveUserStatus(Long userPublicId, Boolean kind) {
        UserModel user = userRepository.findByPublicId(userPublicId).orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getIsActive() == kind)
        {
            throw new RuntimeException("You can't change the active status to the same status.");
        }

        user.setIsActive(kind);

        userRepository.save(user);

        return;
    }


    @Override
    public List<BookResponseAdminDto> listBooks(String title, String author, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());

        var result = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategoryContainingIgnoreCase(title, author, category, pageable);

        return result.stream().map(bookMapper::toResponseAdminDto).toList();
    }
    
}
