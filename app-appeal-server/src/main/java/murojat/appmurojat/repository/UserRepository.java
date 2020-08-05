package murojat.appmurojat.repository;

import murojat.appmurojat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByRegisterCodeEqualsIgnoreCase(String registerCode);
    boolean existsByRegisterCodeAdminEqualsIgnoreCase(String registerCodeAdmin);
    Optional<User> findByEmail(String email);

}
