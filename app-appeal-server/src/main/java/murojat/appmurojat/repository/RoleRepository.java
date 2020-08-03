package murojat.appmurojat.repository;

import murojat.appmurojat.entity.Role;
import murojat.appmurojat.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleName(RoleName roleName);
}
