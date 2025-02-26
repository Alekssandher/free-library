package free_library.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID publicId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(name = "published_at")
    private LocalDate publishedAt;

    private String language;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    private String category;

    public BookModel() {}

    public BookModel( String title, String author, LocalDate publishedAt, String language, String summary, String category)
    {
        this.publicId = UUID.randomUUID();
        this.title = title;
        this.author = author;
        this.publishedAt = publishedAt;
        this.language = language;
        this.summary = summary;
        this.category = category;
    }
}
