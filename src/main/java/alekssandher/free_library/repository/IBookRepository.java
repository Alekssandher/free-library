package alekssandher.free_library.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alekssandher.free_library.entities.book.BookEntity;

@Repository
public interface IBookRepository extends JpaRepository<BookEntity, Long>{
    Optional<BookEntity> findByPublicId(Long publicId);
    void deleteBookByPublicId(Long bookPublicId);

    Page<BookEntity> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategoryContainingIgnoreCase(
        String title, 
        String author, 
        String category,
        Pageable pageable
    );
}
