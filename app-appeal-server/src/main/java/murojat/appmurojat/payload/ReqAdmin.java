package murojat.appmurojat.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class ReqAdmin {
    private UUID id;
    private String registerCode;
}
