package alekssandher.free_library.interfaces.user;

import java.util.List;

import alekssandher.free_library.entities.user.UserEntity;
import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;

public interface IUserService {
    List<UserEntity> findUsersByName(String name);

    UserEntity create(UserEntity model) throws ConflictException;

    void deleteById(long id) throws NotFoundException;

}
