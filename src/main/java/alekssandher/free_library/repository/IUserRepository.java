package alekssandher.free_library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import alekssandher.free_library.model.user.UserModel;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long>{
    boolean existsByEmail(String email);
    
    List<UserModel> findTop10ByNameContainingIgnoreCase(@Param("name") String name);
    
    UserModel findByEmail(String email);
} 