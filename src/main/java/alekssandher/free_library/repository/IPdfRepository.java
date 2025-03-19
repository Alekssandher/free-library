package alekssandher.free_library.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import alekssandher.free_library.model.pdf.PdfEntity;

public interface IPdfRepository extends JpaRepository<PdfEntity, Long> {
    @Modifying
    @Query("DELETE FROM PdfEntity p WHERE p.pdfId = :pdfId")
    int deleteByPdfId(@Param("pdfId") Long pdfId);

    @Modifying
    @Query("DELETE FROM PdfEntity p WHERE p.uploadedAt < :oneHourAgo")
    void deleteOlderThanOneHour(@Param("oneHourAgo") LocalDateTime oneHourAgo);

    @Transactional
    void deleteByPdfIdIn(List<Long> pdfIds);

    List<PdfEntity> findByUploadedAtBefore(LocalDateTime timesamp, Pageable pageable);


}
