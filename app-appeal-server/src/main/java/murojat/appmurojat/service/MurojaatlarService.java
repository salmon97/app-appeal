package murojat.appmurojat.service;

import murojat.appmurojat.entity.FileMurojaat;
import murojat.appmurojat.entity.Murojaatlar;
import murojat.appmurojat.entity.enums.Status;
import murojat.appmurojat.payload.ResMurojaat;
import murojat.appmurojat.payload.ResPageable;
import murojat.appmurojat.repository.FileMurojaatRepository;
import murojat.appmurojat.repository.MurojaatlarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MurojaatlarService {

    @Autowired
    MurojaatlarRepository murojaatlarRepository;

    @Autowired
    FileMurojaatRepository fileMurojaatRepository;

    public ResMurojaat resMurojaat(Murojaatlar murojaatlar) {
        FileMurojaat byMurojaatlar_id = fileMurojaatRepository.findByMurojaatlar_Id(murojaatlar.getId());
        return new ResMurojaat(
                murojaatlar.getId(),
                murojaatlar.getCreatedAt().toString().substring(0, 10),
                murojaatlar.getMurojaatText(),
                murojaatlar.getPhoneNumber(),
                murojaatlar.getCategory(),
                murojaatlar.getDistrict(),
                murojaatlar.getCode(),
                murojaatlar.getStatus(),
                byMurojaatlar_id != null ? byMurojaatlar_id.getFileType() : null,
                byMurojaatlar_id != null ? byMurojaatlar_id.getId() : null,
                murojaatlar.getSource()
        );
    }


    public void deleteAppliction(UUID id) {
        FileMurojaat byMurojaatlar_id = fileMurojaatRepository.findByMurojaatlar_Id(id);
        if (byMurojaatlar_id != null) {
            File file = new File(byMurojaatlar_id.getUploadPath());
            file.delete();
        }
        murojaatlarRepository.deleteById(id);
    }

    public ResPageable pageableDetails(int page, int size,String source) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Murojaatlar> allByStatus = murojaatlarRepository.findAllBySourceAndStatusNot(source,Status.NO_READY,pageable);
        return new ResPageable(
                page, size,
                allByStatus.getTotalElements(),
                allByStatus.getTotalPages(),
                allByStatus.getContent().stream().map(this::resMurojaat).collect(Collectors.toList())
        );
    }

}
