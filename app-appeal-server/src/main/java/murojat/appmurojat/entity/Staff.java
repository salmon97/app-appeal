package murojat.appmurojat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import murojat.appmurojat.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff extends AbsEntity {

    private String name;

    private String phoneNumber;

    @Column(unique = true)
    private Long chatId;

    private String category;
}
