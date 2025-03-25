package alekssandher.free_library.modules.book;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.dto.book.BookResponseDto;
import alekssandher.free_library.entities.user.UserFavoriteEntity;
import alekssandher.free_library.exception.Exceptions.BadRequestException;
import alekssandher.free_library.interfaces.book.IBookService;
import alekssandher.free_library.mappers.BookMapper;
import alekssandher.free_library.modules.auth.JwtService;
import alekssandher.free_library.modules.pdf.PdfService;
import alekssandher.free_library.repository.IBookRepository;
import alekssandher.free_library.repository.IFavoriteRepository;
import alekssandher.free_library.repository.IUserRepository;
import alekssandher.free_library.exception.Exceptions.NotFoundException;

@Service
public class BookService implements IBookService {

    private final IBookRepository repository;
    private final IUserRepository userRepository;
    private final IFavoriteRepository favoriteRepository;
    private final PdfService pdfService;

    private final JwtService jwtService;
    private final BookMapper mapper;


    public BookService(IBookRepository repository, BookMapper mapper, JwtService jwtService, IUserRepository userRepository, IFavoriteRepository favoriteRepository, PdfService pdfService)
    {
        this.repository = repository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
        this.pdfService = pdfService;
    }

    @Override
    public void createBook(BookRequestDto dto, String jwt) {

        String token = jwt.substring(7);
        String email = jwtService.getSubjectFromToken(token);

        var user = userRepository.findByEmail(email);
        
        pdfService.deleteByPdfId(dto.fileId());

        repository.save(mapper.toBookEntity(dto, user));

        return;
    }
    
    @Override
    public List<BookResponseDto> listBooks(String title, String author, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());

        var result = repository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategoryContainingIgnoreCase(title, author, category, pageable);

        return result.stream().map(mapper::toResponseDto).toList();
    }

    @Override
    public void addFavoriteBook(Long bookPublicId, String jwt) {

        String token = jwt.substring(7);
        String email = jwtService.getSubjectFromToken(token);
        var user = userRepository.findByEmail(email);
        var book = repository.findByPublicId(bookPublicId).orElseThrow(() -> new NotFoundException("Book not found with Id: %s".formatted(bookPublicId)));

        if (favoriteRepository.existsByUserIdAndBookPublicId(user.getId(), book.getPublicId())) {
            throw new BadRequestException("Book already in favorites");
        }

        UserFavoriteEntity favorite = new UserFavoriteEntity(user, book);

        favoriteRepository.save(favorite);
    }
}
