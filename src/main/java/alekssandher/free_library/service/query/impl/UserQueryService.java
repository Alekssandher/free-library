package alekssandher.free_library.service.query.impl;

import org.springframework.stereotype.Service;

import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.model.user.UserModel;
import alekssandher.free_library.repository.IUserRepository;
import alekssandher.free_library.service.query.IUserQueryService;

@Service
public class UserQueryService implements IUserQueryService {

    private final IUserRepository repository;

    public UserQueryService(IUserRepository repository) 
    {
        this.repository = repository;
    }

    @Override
    public void verifyEmail(String email) throws ConflictException {
        if(repository.existsByEmail(email))
        {
            var message = "O email %s já está em uso.".formatted(email);

            throw new ConflictException(message);
        }

        return;
    }

    @Override
    public UserModel findById(long id) throws NotFoundException {
        
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado com o id: %s".formatted(id)));
    }
    
}
