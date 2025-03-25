package alekssandher.free_library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import alekssandher.free_library.entities.book.BookEntity;
import alekssandher.free_library.entities.user.UserFavoriteEntity;
import alekssandher.free_library.entities.user.UserFavoriteId;

@Repository
public interface IFavoriteRepository extends JpaRepository<UserFavoriteEntity, UserFavoriteId>{
    boolean existsByUserIdAndBookPublicId(Long userId, Long bookId);
    int deleteByUserIdAndBookId(Long userId, Long bookId);

    @Query("SELECT b FROM BookEntity b JOIN UserFavoriteEntity ufe ON ufe.book.id = b.id WHERE ufe.user.id = :userId")
    List<BookEntity> findBooksByUserId(Long userId);
}
