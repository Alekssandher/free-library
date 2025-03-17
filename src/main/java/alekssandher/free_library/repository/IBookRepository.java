package alekssandher.free_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alekssandher.free_library.model.book.BookModel;

@Repository
public interface IBookRepository extends JpaRepository<BookModel, Long>{
    
}
