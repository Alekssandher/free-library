package free_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import free_library.model.BookModel;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    Boolean findByTitle(BookModel bookModel);
}
