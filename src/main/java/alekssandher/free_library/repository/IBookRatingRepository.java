package alekssandher.free_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import alekssandher.free_library.entities.rating.BookRatingEntity;

@Repository
public interface IBookRatingRepository extends JpaRepository<BookRatingEntity, Long> {
    boolean existsByBookIdAndUserId(Long bookId, Long userId);
    BookRatingEntity findByBookIdAndUserId(Long bookId, Long userId);

    @Query("SELECT AVG(br.rating), COUNT(br) FROM BookRatingEntity br WHERE br.book.id = :bookId")
    Object[] findAverageRatingAndQuantityByBookId( Long bookId);
}
