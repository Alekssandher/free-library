package alekssandher.free_library.interfaces.user;

import alekssandher.free_library.entities.user.UserEntity;

public interface IUserQueryService {
    void verifyEmail(final String email);

    UserEntity findById(long id);

    UserEntity validateCredentials(String email, String password);

    UserEntity findUserByEmail(String email);
}
