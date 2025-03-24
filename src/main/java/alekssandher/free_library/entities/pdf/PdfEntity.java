package alekssandher.free_library.entities.pdf;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PDFS")
public class PdfEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "pdf_id", unique = true, nullable = false)
    private Long pdfId;

    @Column(name = "uploaded_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime uploadedAt = LocalDateTime.now();

    public PdfEntity() {}

    public PdfEntity(Long pdfId)
    {   
        this.pdfId = pdfId;
        this.uploadedAt = LocalDateTime.now();
        
    }

    public String getPdfIdAsString()
    {
        return this.pdfId.toString();
    }

    public Long getPdfIdAsLong()
    {
        return this.pdfId;
    }
}
