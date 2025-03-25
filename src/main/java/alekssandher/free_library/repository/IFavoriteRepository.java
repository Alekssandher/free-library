package alekssandher.free_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alekssandher.free_library.entities.user.UserFavoriteEntity;
import alekssandher.free_library.entities.user.UserFavoriteId;

@Repository
public interface IFavoriteRepository extends JpaRepository<UserFavoriteEntity, UserFavoriteId>{
    boolean existsByUserIdAndBookPublicId(Long userId, Long bookId);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
