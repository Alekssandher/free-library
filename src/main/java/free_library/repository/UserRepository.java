package free_library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import free_library.dto.UserDto;
import free_library.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
    boolean existsById(Long id);
    Optional<UserDto> findByEmail(String email);
} 
