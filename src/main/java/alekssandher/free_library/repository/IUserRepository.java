package alekssandher.free_library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import alekssandher.free_library.entities.user.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long>{
    boolean existsByEmail(String email);
    
    List<UserEntity> findTop10ByNameContainingIgnoreCase(@Param("name") String name);

    Optional<UserEntity> findByPublicId(Long publicId);

    UserEntity findByEmail(String email);
} 