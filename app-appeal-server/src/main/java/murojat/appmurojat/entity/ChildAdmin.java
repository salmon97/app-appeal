package murojat.appmurojat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import murojat.appmurojat.entity.template.AbsEntity;
import murojat.appmurojat.entity.template.AbsNameEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildAdmin extends AbsEntity {

    private String name;

    private String phoneNumber;

    private Long chatId;
}
