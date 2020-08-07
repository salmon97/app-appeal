package murojat.appmurojat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import murojat.appmurojat.entity.enums.Category;
import murojat.appmurojat.entity.enums.Status;
import murojat.appmurojat.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Murojaatlar extends AbsEntity {

    private String phoneNumber;

    private Long chatId;

    private String district;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String category;

    private String source;

    private String code;

    @Column(columnDefinition = "text")
    private String MurojaatText;

    @OneToMany(mappedBy = "murojaatlar",cascade = CascadeType.ALL)
    private List<FileMurojaat> files;
}
