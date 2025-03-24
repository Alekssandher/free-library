package alekssandher.free_library.modules.users;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import alekssandher.free_library.entities.user.UserEntity;
import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.interfaces.user.IUserQueryService;
import alekssandher.free_library.repository.IUserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;

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
            var message = "The email %s is already in use.".formatted(email);

            throw new ConflictException(message);
        }

        return;
    }

    @Override
    public UserEntity findById(long id) throws NotFoundException {
        
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: %s".formatted(id)));
    }

    @Override
    public UserEntity validateCredentials(String email, String password) throws BadRequestException {
        var model = repository.findByEmail(email);
        var message = "The email or password provided is wrong.";
        if(model == null)
        {
            throw new BadRequestException(message);
        }

        var result = BCrypt.verifyer().verify(password.toCharArray(), model.getPassword()).verified;
        
        if(!result)
        {
            throw new BadRequestException(message);
        }
        
        return model;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws NotFoundException {
        var user = repository.findByEmail(email);
        if (user == null)
        {
            throw new NotFoundException("Email not found.");
        }

        return user;
    }

    

}
