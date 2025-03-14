package alekssandher.free_library.interfaces.user;

import java.util.List;

import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.model.user.UserModel;

public interface IUserService {
    List<UserModel> findUsersByName(String name);

    UserModel create(UserModel model) throws ConflictException;

    void deleteById(long id) throws NotFoundException;

}
