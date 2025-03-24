package alekssandher.free_library.modules.pdf;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import alekssandher.free_library.entities.pdf.PdfEntity;
import alekssandher.free_library.repository.IPdfRepository;
import jakarta.annotation.PostConstruct;

@Service
public class PdfCleanUpService {
    private final IPdfRepository repository;
    private final Cloudinary cloudinary;

    public PdfCleanUpService(IPdfRepository repository, Cloudinary cloudinary)
    {
        this.repository = repository;
        this.cloudinary = cloudinary;
    }

    @Scheduled(fixedRate = 1800000)

    public void cleanUpOldPdfs() throws Exception
    {

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        Pageable limit = PageRequest.of(0, 100);
        
        List<PdfEntity> pdfs = repository.findByUploadedAtBefore(oneHourAgo, limit);

        if(pdfs.isEmpty()) return;

        List<String> cloudinaryIds = pdfs.stream().map(PdfEntity::getPdfIdAsString).collect(Collectors.toList());

        cloudinary.api().deleteResources(cloudinaryIds, ObjectUtils.asMap("resource_type", "raw"));

        List<Long> pdfIds = pdfs.stream()
                .map(PdfEntity::getPdfIdAsLong)
                .collect(Collectors.toList());

        repository.deleteByPdfIdIn(pdfIds);
    }

    @PostConstruct
    public void testCleanUp() throws Exception {
        cleanUpOldPdfs();
    }
}
