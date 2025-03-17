package alekssandher.free_library.modules.book;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import alekssandher.free_library.config.SnowFlakeSing;
import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.interfaces.book.IBookService;
import alekssandher.free_library.mappers.BookMapper;
import alekssandher.free_library.modules.auth.JwtService;
import alekssandher.free_library.repository.IBookRepository;
import alekssandher.free_library.repository.IUserRepository;

@Service
public class BookService implements IBookService {

    private final IBookRepository repository;
    private final IUserRepository userRepository;

    private final JwtService jwtService;
    private final BookMapper mapper;

    private final Cloudinary cloudinary;

    public BookService(IBookRepository repository, BookMapper mapper, JwtService jwtService, IUserRepository userRepository, Cloudinary cloudinary)
    {
        this.repository = repository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public void uploadBook(BookRequestDto dto, String jwt) {

        String token = jwt.substring(7);
        String email = jwtService.getSubjectFromToken(token);

        var user = userRepository.findByEmail(email);

        
        repository.save(mapper.toBookModel(dto, user));

        return;
    }

    @Override
    public String uploadPdf(MultipartFile pdf) throws IOException {

        Long pdfId = SnowFlakeSing.getInstance().nextId();

        var uploadResult = cloudinary.uploader().upload(pdf.getBytes(), ObjectUtils.asMap(
            "resource_type", "raw",  
            "public_id", pdfId.toString()
        ));

        return uploadResult.get("secure_url").toString();
    }
    
}
