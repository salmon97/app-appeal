package murojat.appmurojat.Controller;

import murojat.appmurojat.payload.ApiResponse;
import murojat.appmurojat.payload.ReqAdmin;
import murojat.appmurojat.payload.ReqStaff;
import murojat.appmurojat.repository.StaffRepository;
import murojat.appmurojat.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class StaffController {

    @Autowired
    StaffService staffService;

    @Autowired
    StaffRepository staffRepository;

    @PutMapping("/editStaff")
    public HttpEntity<?> edit(@RequestBody ReqStaff reqStaff) {
        if (staffRepository.existsById(reqStaff.getId())) {
            return ResponseEntity.ok(new ApiResponse("ok", true, staffService.editStaff(reqStaff)));
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/editRegisterCode")
    public HttpEntity<?> editRegisterStaffBot(@RequestBody ReqAdmin reqAdmin) {
        ApiResponse apiResponse = staffService.editRegisterStaffBot(reqAdmin);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping("/deleteStaff/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        if (staffRepository.existsById(id)) {
            UUID uuid = staffService.deleteStaff(id);
            return ResponseEntity.ok(new ApiResponse(uuid.toString(), true));
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }
}
