package alekssandher.free_library.modules.pdf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import alekssandher.free_library.entities.pdf.PdfEntity;
import alekssandher.free_library.exception.Exceptions.InternalErrorException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.interfaces.book.IBookQueryService;
import alekssandher.free_library.interfaces.pdf.IPdfService;
import alekssandher.free_library.repository.IPdfRepository;
import alekssandher.free_library.utils.Snowflake;
import jakarta.transaction.Transactional;

@Service
public class PdfService implements IPdfService{
    private final Cloudinary cloudinary;
    private final Snowflake snowflake;

    private final IPdfRepository repository;
    private final IBookQueryService bookService;

    public PdfService(Cloudinary cloudinary, IPdfRepository repository, IBookQueryService bookService, Snowflake snowflake)
    {
        this.cloudinary = cloudinary;
        this.repository = repository;
        this.bookService = bookService;
        this.snowflake = snowflake;
    }
    
    @Override
    public String uploadPdf(MultipartFile pdf)
    {

        Long pdfId = snowflake.nextId();

        try {

            cloudinary.uploader().upload(pdf.getBytes(), ObjectUtils.asMap(
                "resource_type", "raw",  
                "type", "private",
                "public_id", pdfId.toString()
            ));

        } catch (Exception ex) {
            System.err.printf("Error generating temporary file url: %s%n", ex.getMessage());

            throw new InternalErrorException("There was an internal error, try again later.");
        }

        repository.save(new PdfEntity(pdfId));

        return pdfId.toString();
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

    @Override
    public String getFileUrl(Long fileId) 
    {
        long expiresAt = (System.currentTimeMillis() / 1000) + 300;
        bookService.findBookByFileId(fileId);
        try {

            Map<String, Object> options = new HashMap<>();

            options.put("expires_at", expiresAt);
            options.put("resource_type", "raw");
            options.put("attachment", true);
            options.put("format", "pdf");

            String signature = cloudinary.apiSignRequest(options, cloudinary.config.apiSecret);

            var url = cloudinary.privateDownload(fileId.toString(), signature, options);
            
            System.out.println("Url: " + url);

            return url;
        } catch (Exception ex) {

            System.err.printf("Error generating temporary file url: %s%n", ex.getMessage());

            throw new InternalErrorException("There was an error while generating your url, try again later.");
        }
    }
    
}
