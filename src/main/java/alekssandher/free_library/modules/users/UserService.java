package alekssandher.free_library.modules.users;

import java.util.List;

import org.springframework.stereotype.Service;

import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.interfaces.user.IUserQueryService;
import alekssandher.free_library.interfaces.user.IUserService;
import alekssandher.free_library.model.user.UserModel;
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
    public List<UserModel> findUsersByName(String name) {
        
        
        return repository.findTop10ByNameContainingIgnoreCase(name);
    }

    @Override
    public UserModel create(UserModel model) throws ConflictException {
        queryService.verifyEmail(model.getEmail());

        model.setPassword(BCrypt.withDefaults().hashToString(12 , model.getPassword().toCharArray()));
        System.out.print("saved model with name: " + model.getName());
        return repository.save(model);
    }

    @Override
    public void deleteById(long id) throws NotFoundException {
        queryService.findById(id);
        repository.deleteById(id);

        return;
    }  
    
}
