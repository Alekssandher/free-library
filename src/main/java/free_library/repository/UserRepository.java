package free_library.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import free_library.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
    boolean existsById(Long id);
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByPublicId(UUID publicId);
} 
