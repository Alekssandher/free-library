package alekssandher.free_library.modules.favorite;

import java.util.List;

import org.springframework.stereotype.Service;

import alekssandher.free_library.dto.book.BookResponseDto;
import alekssandher.free_library.entities.user.UserFavoriteEntity;
import alekssandher.free_library.exception.Exceptions.BadRequestException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.interfaces.favorite.IFavoriteService;
import alekssandher.free_library.mappers.BookMapper;
import alekssandher.free_library.modules.auth.JwtService;
import alekssandher.free_library.repository.IBookRepository;
import alekssandher.free_library.repository.IFavoriteRepository;
import alekssandher.free_library.repository.IUserRepository;
import jakarta.transaction.Transactional;

@Service
public class FavoriteService implements IFavoriteService {

    private final IBookRepository repository;
    private final IUserRepository userRepository;
    private final IFavoriteRepository favoriteRepository;

    private final JwtService jwtService;

    private final BookMapper bookMapper;

    public FavoriteService(IBookRepository repository,JwtService jwtService, IUserRepository userRepository, IFavoriteRepository favoriteRepository, BookMapper bookMapper)
    {
        this.repository = repository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public void addFavoriteBook(Long bookPublicId, String jwt) {

        String email = extractEmailFromToken(jwt);

        var user = userRepository.findByEmail(email);
        var book = repository.findByPublicId(bookPublicId).orElseThrow(() -> new NotFoundException("Book not found with Id: %s".formatted(bookPublicId)));

        if (favoriteRepository.existsByUserIdAndBookPublicId(user.getId(), book.getPublicId())) {
            throw new BadRequestException("Book already in favorites");
        }

        UserFavoriteEntity favorite = new UserFavoriteEntity(user, book);

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(Long bookPublicId, String jwt) {

        String email = extractEmailFromToken(jwt);
        var user = userRepository.findByEmail(email);
        var book = repository.findByPublicId(bookPublicId).orElseThrow(() -> new NotFoundException("Book not found with Id: %s".formatted(bookPublicId)));

        if(favoriteRepository.deleteByUserIdAndBookId(user.getId(), book.getId()) == 0)
        {
            throw new BadRequestException("The book provided is not in your favorites.");
        }
        
    }

    @Override
    public List<BookResponseDto> getFavorites(String jwt) {
        var email = extractEmailFromToken(jwt);
        var user = userRepository.findByEmail(email);
        var favorites = favoriteRepository.findBooksByUserId(user.getId());

        return favorites.stream().map(bookMapper::toResponseDto).toList();
    }

    private String extractEmailFromToken(String jwt)
    {
        var token = jwt.substring(7);
        return jwtService.getSubjectFromToken(token);
    }
}
