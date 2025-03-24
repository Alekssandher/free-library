package alekssandher.free_library.interfaces.user;

import org.apache.coyote.BadRequestException;

import alekssandher.free_library.entities.user.UserEntity;
import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;

public interface IUserQueryService {
    void verifyEmail(final String email) throws ConflictException;

    UserEntity findById(long id) throws NotFoundException;

    UserEntity validateCredentials(String email, String password) throws BadRequestException;

    UserEntity findUserByEmail(String email) throws NotFoundException;
}
