package alekssandher.free_library.modules.users;

import java.util.List;

import org.springframework.stereotype.Service;

import alekssandher.free_library.entities.user.UserEntity;
import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.interfaces.user.IUserQueryService;
import alekssandher.free_library.interfaces.user.IUserService;
import alekssandher.free_library.repository.IUserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService implements IUserService {

    private final IUserRepository repository;
    private final IUserQueryService queryService;

    public UserService(IUserRepository repository, IUserQueryService queryService)
    {
        this.repository = repository;
        this.queryService = queryService;
    }

    @Override
    public List<UserEntity> findUsersByName(String name) {
        
        
        return repository.findTop10ByNameContainingIgnoreCase(name);
    }

    @Override
    public UserEntity create(UserEntity model) throws ConflictException {
        queryService.verifyEmail(model.getEmail());

        model.setPassword(BCrypt.withDefaults().hashToString(12 , model.getPassword().toCharArray()));
        
        return repository.save(model);
    }

    @Override
    public void deleteById(long id) throws NotFoundException {
        queryService.findById(id);
        repository.deleteById(id);

        return;
    }

    
}
