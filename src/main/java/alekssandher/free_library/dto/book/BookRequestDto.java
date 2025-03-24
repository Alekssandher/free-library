package alekssandher.free_library.dto.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookRequestDto(
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    String title,

    @Size(max = 100, message = "Title must be at most 100 characters")
    @NotBlank(message = "Author is required") 
    String author,

    @Size(max = 500, message = "Description must be at most 500 characters")
    String description,

    @NotBlank(message = "Category is required")
    String category,

    @NotBlank(message = "Language is required")
    String language,

    @NotBlank(message = "Publisher is required")
    String publisher,

    @Min(value = 1, message = "Year must be greater than 1")
    @Max(value = 2100, message = "Year must be less than 2100")
    Short publishedAt,

    @NotNull(message = "File ID is required")
    @Positive(message = "File ID must be a positive number")
    Long fileId
) {}