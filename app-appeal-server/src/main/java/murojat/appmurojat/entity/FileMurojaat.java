package murojat.appmurojat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import murojat.appmurojat.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileMurojaat extends AbsEntity {

    private String fileId;
    private String extension;
    private Long fileSize;
    private String contentType;
    private String uploadPath;
    private String fileType;
    @ManyToOne
    private Murojaatlar murojaatlar;
}
