package alekssandher.free_library.service.query;

import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.model.user.UserModel;

public interface IUserQueryService {
    void verifyEmail(final String email) throws ConflictException;

    UserModel findById(long id) throws NotFoundException;
}
