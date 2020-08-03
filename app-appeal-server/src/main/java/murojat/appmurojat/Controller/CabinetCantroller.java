package murojat.appmurojat.Controller;

import murojat.appmurojat.payload.ApiResponse;
import murojat.appmurojat.payload.ResStaff;
import murojat.appmurojat.repository.MurojaatlarRepository;
import murojat.appmurojat.repository.StaffRepository;
import murojat.appmurojat.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CabinetCantroller {

    @Autowired
    MurojaatlarRepository murojaatlarRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    StaffService staffService;

    @GetMapping("/getCount")
    public HttpEntity<?> getCount() {
        java.sql.Date date = new Date(System.currentTimeMillis());
        Integer count = staffRepository.getAllCount();
        Integer dayCount1 = murojaatlarRepository.getDayCount(date);
        Integer allCount = murojaatlarRepository.getAllCount();
        Integer checked = murojaatlarRepository.getChecked();
        Integer checkingNow = murojaatlarRepository.getCheckingNow();
        Integer noChecked = murojaatlarRepository.getNoChecked();
        Integer[] counts = {count, dayCount1,allCount,checked,noChecked,checkingNow};
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/getStaff")
    public HttpEntity<?> getStaff(){
        List<ResStaff> resStaffs = staffService.resStaffs();
        return ResponseEntity.ok(new ApiResponse("ok",true,resStaffs));
    }
}
