package alekssandher.free_library.modules.pdf;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import alekssandher.free_library.entities.pdf.PdfEntity;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.interfaces.pdf.IPdfService;
import alekssandher.free_library.repository.IPdfRepository;
import alekssandher.free_library.utils.Snowflake;
import jakarta.transaction.Transactional;

@Service
public class PdfService implements IPdfService{
    private final Cloudinary cloudinary;
    private final Snowflake snowflake;

    private final IPdfRepository repository;

    public PdfService(Cloudinary cloudinary, IPdfRepository repository, Snowflake snowflake)
    {
        this.cloudinary = cloudinary;
        this.repository = repository;
        this.snowflake = snowflake;
    }
    
    @Override
    public String uploadPdf(MultipartFile pdf) throws IOException {

        Long pdfId = snowflake.nextId();

        var uploadResult = cloudinary.uploader().upload(pdf.getBytes(), ObjectUtils.asMap(
            "resource_type", "raw",  
            "public_id", pdfId.toString()
        ));

        repository.save(new PdfEntity(pdfId));

        String[] arrayResult = uploadResult.get("secure_url").toString().split("/");
        String lastElement = arrayResult[arrayResult.length - 1];

        return lastElement;
    }

    @Override
    @Transactional
    public void deleteByPdfId(Long id) {
        int deleteCount = repository.deleteByPdfId(id);

        if(deleteCount == 0)
        {
            throw new NotFoundException("PDF with id: " + id + " not found");
        }
    }
    
}
