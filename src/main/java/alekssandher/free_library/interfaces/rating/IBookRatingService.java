package alekssandher.free_library.interfaces.rating;

import alekssandher.free_library.dto.rating.BookRatingResponseDto;

public interface IBookRatingService {

    void rateBook(Long bookPublicId, int rating, String jwt);

    BookRatingResponseDto getRatings(Long bookPublicId);
    
}
