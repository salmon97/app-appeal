package murojat.appmurojat.service;

import murojat.appmurojat.entity.Staff;
import murojat.appmurojat.entity.User;
import murojat.appmurojat.payload.ApiResponse;
import murojat.appmurojat.payload.ReqAdmin;
import murojat.appmurojat.payload.ReqStaff;
import murojat.appmurojat.payload.ResStaff;
import murojat.appmurojat.repository.StaffRepository;
import murojat.appmurojat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    UserRepository userRepository;

    public List<ResStaff> resStaffs() {
        List<Staff> all = staffRepository.findAll();
        List<ResStaff> resApplicants = new ArrayList<>();
        for (Staff staff : all) {
            resApplicants.add(
                    new ResStaff(
                            staff.getId(),
                            staff.getPhoneNumber(),
                            staff.getName(),
                            staff.getCategory()));
        }
        return resApplicants;
    }


    public ResStaff editStaff(ReqStaff reqStaff) {
        Staff staff = staffRepository.findById(reqStaff.getId()).orElseThrow(() -> new ResourceNotFoundException("getSatff"));
        staff.setName(reqStaff.getName());
        staff.setCategory(reqStaff.getCategory());
        staff.setPhoneNumber(reqStaff.getPhoneNum());
        Staff save = staffRepository.save(staff);
        return new ResStaff(
                save.getId(),
                save.getPhoneNumber(),
                save.getName(),
                save.getCategory()
        );
    }


    public ApiResponse editRegisterStaffBot(ReqAdmin reqAdmin) {
        Optional<User> byId = userRepository.findById(reqAdmin.getId());
        if (byId.isPresent()) {
            byId.get().setRegisterCode(reqAdmin.getRegisterCode());
            userRepository.save(byId.get());
            return new ApiResponse("ok", true);
        }
        return new ApiResponse("no", false);
    }


    public UUID deleteStaff(UUID id) {
        staffRepository.deleteById(id);
        return id;
    }

}
