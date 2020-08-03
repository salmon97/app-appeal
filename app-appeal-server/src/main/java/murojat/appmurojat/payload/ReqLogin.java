package murojat.appmurojat.payload;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReqLogin {
    @Pattern(regexp = "[a-zA-Z-№]{2,7}", message = "Passport serial must be between 2 and 7 characters. Username must be alpha numerical.")
    private String email;
    @Pattern(regexp = "^(?:(?=.*?\\p{N})(?=.*?[\\p{S}\\p{P} ])(?=.*?\\p{Lu})(?=.*?\\p{Ll}))[^\\p{C}]{4,16}$", message = "Uzunligi 8-16 oralig'ida. Parolda kamida bitta katta harf, son va belgi b'lishi shart.")
    private String password;

}
