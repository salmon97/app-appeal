package murojat.appmurojat.repository;

import murojat.appmurojat.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
