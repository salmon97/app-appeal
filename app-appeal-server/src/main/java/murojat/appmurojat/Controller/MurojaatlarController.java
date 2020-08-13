package murojat.appmurojat.Controller;

import murojat.appmurojat.entity.FileMurojaat;
import murojat.appmurojat.payload.ApiResponse;
import murojat.appmurojat.payload.ResPageable;
import murojat.appmurojat.repository.FileMurojaatRepository;
import murojat.appmurojat.repository.MurojaatlarRepository;
import murojat.appmurojat.service.MurojaatlarService;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MurojaatlarController {

    @Autowired
    MurojaatlarService murojaatlarService;

    @Autowired
    MurojaatlarRepository murojaatlarRepository;

    @Autowired
    FileMurojaatRepository fileMurojaatRepository;

    @GetMapping("/applications/{source}")
    public HttpEntity<?> searchCompany(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,@PathVariable String source) {
        ResPageable resPageable = murojaatlarService.pageableDetails(page, size,source);
        return ResponseEntity.ok(resPageable);
    }

    @DeleteMapping("/deleteApplication/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        if (murojaatlarRepository.existsById(id)) {
            murojaatlarService.deleteAppliction(id);
            return ResponseEntity.ok(new ApiResponse(id.toString(), true));
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getFile/{id}")
    public ResponseEntity<?> getFile(@PathVariable UUID id) throws IOException {
        FileMurojaat fileMurojaat = fileMurojaatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getFile"));
        File file = new File(fileMurojaat.getUploadPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" +file.getName()+"\"")
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                .body(Files.readAllBytes(file.toPath()));
    }

}
