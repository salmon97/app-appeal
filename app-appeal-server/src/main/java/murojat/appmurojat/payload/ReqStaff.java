package murojat.appmurojat.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class ReqStaff {
    private UUID id;
    private String name;
    private String category;
    private String phoneNum;
    private String registerCode;
}
