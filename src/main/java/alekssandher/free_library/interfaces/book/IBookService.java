package alekssandher.free_library.interfaces.book;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import alekssandher.free_library.dto.book.BookRequestDto;

public interface IBookService {

    void uploadBook(BookRequestDto dto, String jwt);

    String uploadPdf(MultipartFile pdf) throws IOException;
    
}