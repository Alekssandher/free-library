package alekssandher.free_library.modules.pdf;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.interfaces.pdf.IPdfService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pdfs")
@SecurityRequirement(name = "Authorization")
public class PdfController {
    private final IPdfService service;

    public PdfController(IPdfService service)
    {
        this.service = service;
    }

    @PostMapping("uploadPdf")
    public ResponseEntity<CreatedResponse<String>> uploadPdf(
        @RequestParam("pdf") 
        MultipartFile pdf, 
        
        HttpServletRequest request
        ) throws IOException
    {
        var result = service.uploadPdf(pdf);
        

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse<String>(request, result));
    }
}
