package murojat.appmurojat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import murojat.appmurojat.bot.Constant;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResStaff {
    private UUID id;
    private String phoneNumber;
    private String name;
    private String category;
}
