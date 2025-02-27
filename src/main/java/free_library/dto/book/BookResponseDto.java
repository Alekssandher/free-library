package free_library.dto.book;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class BookResponseDto {
    
    private String id;
    private String title;
    private String author;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishedAt;

    private String language;
    private String summary;
    private String category;


    public BookResponseDto() {
    }

    public BookResponseDto(String id, String title, String author, LocalDate publishedAt, String language, String summary, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedAt = publishedAt;
        this.language = language;
        this.summary = summary;
        this.category = category;
    }
}
