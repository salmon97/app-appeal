package murojat.appmurojat.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import murojat.appmurojat.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ForgotPassword extends AbsEntity {

    private int forgotPasswordCode;

    @OneToOne(optional = false)
    private User user;
}
