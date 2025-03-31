package alekssandher.free_library.modules.book;

import org.springframework.stereotype.Service;

import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.interfaces.book.IBookQueryService;
import alekssandher.free_library.repository.IBookRepository;

@Service
public class BookQueryService implements IBookQueryService{

    private final IBookRepository repository;

    public BookQueryService(IBookRepository repository)
    {   
        this.repository = repository;
    }

    @Override
    public Void findBookByFileId(Long fileId)
    {
        repository.findByFileId(fileId).orElseThrow(() -> new NotFoundException("There's no book associated with ID: %s".formatted(fileId)));

        return null;

    }
}
