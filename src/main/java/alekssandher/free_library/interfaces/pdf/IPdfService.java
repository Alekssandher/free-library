package alekssandher.free_library.interfaces.pdf;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IPdfService {
    String uploadPdf(MultipartFile pdf) throws IOException;

    void deleteByPdfId(Long id);
}
