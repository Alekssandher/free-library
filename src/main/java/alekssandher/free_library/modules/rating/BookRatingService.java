package alekssandher.free_library.modules.rating;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import alekssandher.free_library.dto.rating.BookRatingResponseDto;
import alekssandher.free_library.entities.rating.BookRatingEntity;
import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.exception.Exceptions.InternalErrorException;
import alekssandher.free_library.interfaces.rating.IBookRatingService;
import alekssandher.free_library.modules.auth.JwtService;
import alekssandher.free_library.repository.IBookRatingRepository;
import alekssandher.free_library.repository.IBookRepository;
import alekssandher.free_library.repository.IUserRepository;

@Service
public class BookRatingService implements IBookRatingService {

    private final IBookRatingRepository repository;
    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;

    private final JwtService jwtService;

    public BookRatingService(IBookRatingRepository repository, JwtService jwtService, IUserRepository userRepository, IBookRepository bookRepository)
    {
        this.repository = repository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void rateBook(Long bookPublicId, int rating, String jwt) 
    {
        var email = extractEmailFromToken(jwt);
        var user = userRepository.findByEmail(email);
        var book = bookRepository.findByPublicId(bookPublicId).orElseThrow(() -> new NotFoundException("Book not found with Id: %s".formatted(bookPublicId)));

        if(repository.existsByBookIdAndUserId(book.getId(), user.getId()))
        {
            throw new ConflictException("You can't rate a book twice.");
        }

        BookRatingEntity entity = new BookRatingEntity(book, user, rating);

        repository.save(entity);
    }

    @Override
    public BookRatingResponseDto getRatings(Long bookPublicId) {
        var book = bookRepository.findByPublicId(bookPublicId).orElseThrow(() -> new NotFoundException("Book not found with Id: %s".formatted(bookPublicId)));

        var result = repository.findAverageRatingAndQuantityByBookId(book.getId());

        if (result != null ) {
            
            Object[] innerResult = (Object[]) result[0];

            Double averageRating = (Double) innerResult[0];
            Long ratingCount = (Long) innerResult[1];

            return new BookRatingResponseDto(averageRating, ratingCount);

        } else {
            throw new InternalErrorException("Something went wrong.");
        }

    }
    
    private String extractEmailFromToken(String jwt)
    {
        var token = jwt.substring(7);
        return jwtService.getSubjectFromToken(token);
    }
}
