package alekssandher.free_library.modules.pdf;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.OkResponse;
import alekssandher.free_library.dto.response.ErrorResponses.Forbidden;
import alekssandher.free_library.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.free_library.dto.response.ErrorResponses.MethodNotAllowed;
import alekssandher.free_library.dto.response.ErrorResponses.NotFound;
import alekssandher.free_library.dto.response.ErrorResponses.Unauthorized;
import alekssandher.free_library.interfaces.pdf.IPdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("pdfs")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Pdfs", description = "Endpoint to upload pdf to cloud.")
@ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unauthorized.class))),
        @ApiResponse(responseCode = "401", description = "Forbidden",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class))),
        @ApiResponse(responseCode = "405", description = "Mehod Not Allowed",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MethodNotAllowed.class)))
})
public class PdfController {
    private final IPdfService service;

    public PdfController(IPdfService service)
    {
        this.service = service;
    }

    @Operation(summary = "Upload a file", description = "Uploads a file to the cloud and receives its ID.")
    @ApiResponse(responseCode = "201", description = "File uploaded successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class)))
    @PostMapping(value = "uploadPdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreatedResponse<String>> uploadPdf(
        @RequestParam() 
        MultipartFile pdf, 
        
        HttpServletRequest request
        )
    {
        var result = service.uploadPdf(pdf);
        

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse<String>(request, result));
    }

    @Operation(summary = "Get file download url", description = "Get a temporary url that expires in 5 minutes for the fileId passed.")
    @ApiResponse(responseCode = "200", description = "File found and url generated successfully.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OkResponse.class)))
    @ApiResponse(responseCode = "404", description = "Book not.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @GetMapping("{fileId}")
    public ResponseEntity<OkResponse<String>> getFileUrl(@Valid @RequestParam Long fileId, HttpServletRequest request)
    {
        String url = service.getFileUrl(fileId);

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse<String>(url, request));
    }
}
