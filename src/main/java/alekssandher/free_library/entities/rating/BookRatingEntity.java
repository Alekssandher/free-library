package alekssandher.free_library.entities.rating;

import java.time.LocalDateTime;

import alekssandher.free_library.entities.book.BookEntity;
import alekssandher.free_library.entities.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BOOK_RATINGS")
public class BookRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "rated_at", nullable = false)
    private LocalDateTime ratedAt = LocalDateTime.now();

    public BookRatingEntity() {}

    public BookRatingEntity(BookEntity book, UserEntity user, int rating) {
        this.book = book;
        this.user = user;
        this.rating = rating;
    }
}
