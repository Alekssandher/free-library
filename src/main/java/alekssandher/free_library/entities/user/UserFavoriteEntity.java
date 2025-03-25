package alekssandher.free_library.entities.user;

import alekssandher.free_library.entities.book.BookEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_FAVORITES")
@IdClass(UserFavoriteId.class)
public class UserFavoriteEntity {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    public UserFavoriteEntity() {}

    public UserFavoriteEntity(UserEntity user, BookEntity book) {
        this.user = user;
        this.book = book;
    }
}
