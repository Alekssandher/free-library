package free_library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import free_library.model.BookModel;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    Optional<BookModel> findByPublicId(String id);

}
