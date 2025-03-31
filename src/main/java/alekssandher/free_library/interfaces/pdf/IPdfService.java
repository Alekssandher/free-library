package alekssandher.free_library.interfaces.pdf;

import org.springframework.web.multipart.MultipartFile;

public interface IPdfService {
    String uploadPdf(MultipartFile pdf);

    void deleteByPdfId(Long id);

    String getFileUrl(Long fileId);
}
