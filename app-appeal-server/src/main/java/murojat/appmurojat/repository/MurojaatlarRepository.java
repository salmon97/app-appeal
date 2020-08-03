package murojat.appmurojat.repository;

import murojat.appmurojat.entity.Murojaatlar;
import murojat.appmurojat.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public interface MurojaatlarRepository extends JpaRepository<Murojaatlar, UUID> {

    @Query(value = "select * from murojaatlar where chat_id = :chat_id order by created_at desc limit 1",nativeQuery = true)
    Murojaatlar getByChatId(long chat_id);

    @Query(value = "select count(id) from murojaatlar where DATE(created_at) =:dayTime",nativeQuery = true)
    Integer getDayCount(Date dayTime);

    @Query(value = "select count(id) from murojaatlar",nativeQuery = true)
    Integer getAllCount();

    @Query(value = "select count(id) from murojaatlar where status = 'READY'",nativeQuery = true)
    Integer getCheckingNow();

    @Query(value = "select count(id) from murojaatlar where status = 'NO_CHECKED'",nativeQuery = true)
    Integer getNoChecked();

    @Query(value = "select count(id) from murojaatlar where status = 'CHECKED'",nativeQuery = true)
    Integer getChecked();


    @Query(value = "select * from murojaatlar where status = 'READY' and date(created_at) = (select date(now()) - 3)",nativeQuery = true)
    List<Murojaatlar> getByStatusAndDay3();

    @Query(value = "select * from murojaatlar where status = 'READY' and date(created_at) = (select date(now()) - 1)",nativeQuery = true)
    List<Murojaatlar> getByStatusAndDay1();

    @Query(value = "select * from murojaatlar where status = 'READY' and date(created_at) = (select date(now()) - 8)",nativeQuery = true)
    List<Murojaatlar> getByOut();

    @Query(value = "select * from murojaatlar where status = 'NO_READY' and date(created_at) < date(now())",nativeQuery = true)
    List<Murojaatlar> getByNoReady();
}
