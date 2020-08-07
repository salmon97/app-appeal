package murojat.appmurojat.repository;

import murojat.appmurojat.entity.ChildAdmin;
import murojat.appmurojat.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChildAdminRepository extends JpaRepository<ChildAdmin, UUID> {

    @Query(value = "select * from child_admin where chat_id = :chatId order by created_at desc limit 1",nativeQuery = true)
    ChildAdmin getByChatId(Long chatId);

    boolean existsByChatId(long chatId);

    @Query(value = "select count(id) from staff ",nativeQuery = true)
    Integer getAllCount();
}
