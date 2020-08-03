package murojat.appmurojat.repository;

import murojat.appmurojat.entity.FileMurojaat;
import murojat.appmurojat.entity.Murojaatlar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileMurojaatRepository extends JpaRepository<FileMurojaat, UUID> {

    FileMurojaat findByMurojaatlar_Id(UUID murojaatlar_id);
}
