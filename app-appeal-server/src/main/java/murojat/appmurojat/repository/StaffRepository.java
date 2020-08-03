package murojat.appmurojat.repository;

import murojat.appmurojat.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {

    @Query(value = "select * from Staff  where chat_id = :chatId  order by created_at desc limit 1",nativeQuery = true)
    Staff getByChatId(long chatId);

    List<Staff> findAllByChatId(Long chatId);

    @Query(value = "select count(id) from staff ",nativeQuery = true)
    Integer getAllCount();
}
