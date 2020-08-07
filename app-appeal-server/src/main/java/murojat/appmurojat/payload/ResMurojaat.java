package murojat.appmurojat.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import murojat.appmurojat.entity.enums.Status;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResMurojaat {
   private String definition;
   private String code;
   private UUID id;
   private String created_at;
   private String text;
   private String mainMurojaat;
   private String phoneNumber;
   private String category;
   private String district;
   private String fileType;
   private Status status;
   private UUID fileId;
   private String source;

   public ResMurojaat(String definition, String code, UUID id, String created_at, String text, String mainMurojaat, String phoneNumber, String category, String district, String fileType) {
      this.definition = definition;
      this.code = code;
      this.id = id;
      this.created_at = created_at;
      this.text = text;
      this.mainMurojaat = mainMurojaat;
      this.phoneNumber = phoneNumber;
      this.category = category;
      this.district = district;
      this.fileType = fileType;
   }

   public ResMurojaat(UUID id, String created_at, String text, String phoneNumber, String category, String district, String code,Status status,String fileType,UUID fileId,String source) {
      this.id = id;
      this.created_at = created_at;
      this.text = text;
      this.phoneNumber = phoneNumber;
      this.category = category;
      this.district = district;
      this.code = code;
      this.status =status;
      this.fileType =fileType;
      this.fileId = fileId;
      this.source = source;
   }
}
