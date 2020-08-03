package murojat.appmurojat.repository;

import murojat.appmurojat.entity.ForgotPassword;
import murojat.appmurojat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, UUID> {

}
