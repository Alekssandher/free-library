package alekssandher.free_library.entities.book;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import alekssandher.free_library.config.SnowFlakeSing;
import alekssandher.free_library.entities.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ForeignKey;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "BOOKS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_BOOK_OPLOADER", columnNames = "public_id")
    }
)
@Getter
@Setter
public class BookEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "public_id", unique = true, nullable = false)
    private Long publicId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String author;

    @Column(nullable = false, length = 400)
    private String description;

    @Column(nullable = false, length = 20)
    private String language; 

    @Column(nullable = false, length = 255)
    private String publisher;

    @Column(nullable = false, length = 100)
    private String category;
    
    @Column(name = "published_at")
    private Short publishedAt;

    @Column(nullable = false, length = 500)
    private Long fileId;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false, referencedColumnName = "public_id", foreignKey = @ForeignKey(name = "FK_BOOK_UPLOADER"))
    private UserEntity uploadedBy;

    public BookEntity() {}

    public BookEntity(String title, String author, String description, String language, String publisher, String category, Short plubishedAt, Long fileId, UserEntity uploadedBy)
    {
        this.publicId = SnowFlakeSing.getInstance().nextId();
        this.title = title;
        this.author = author;
        this.description = description;
        this.language = language;
        this.publisher = publisher;
        this.category = category;
        this.publishedAt = plubishedAt;
        this.fileId = fileId;
        this.uploadedBy = uploadedBy;

        this.uploadedAt = LocalDateTime.now();
        
    }
}
