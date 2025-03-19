package alekssandher.free_library.modules.book;

import org.springframework.stereotype.Service;

import alekssandher.free_library.dto.book.BookRequestDto;
import alekssandher.free_library.interfaces.book.IBookService;
import alekssandher.free_library.mappers.BookMapper;
import alekssandher.free_library.modules.auth.JwtService;
import alekssandher.free_library.modules.pdf.PdfService;
import alekssandher.free_library.repository.IBookRepository;
import alekssandher.free_library.repository.IUserRepository;

@Service
public class BookService implements IBookService {

    private final IBookRepository repository;
    private final IUserRepository userRepository;
    private final PdfService pdfService;

    private final JwtService jwtService;
    private final BookMapper mapper;


    public BookService(IBookRepository repository, BookMapper mapper, JwtService jwtService, IUserRepository userRepository, PdfService pdfService)
    {
        this.repository = repository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.pdfService = pdfService;
    }

    @Override
    public void createBook(BookRequestDto dto, String jwt) {

        String token = jwt.substring(7);
        String email = jwtService.getSubjectFromToken(token);

        var user = userRepository.findByEmail(email);
        
        pdfService.deleteByPdfId(dto.fileId());

        repository.save(mapper.toBookModel(dto, user));

        return;
    }
    
}
