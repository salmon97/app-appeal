package murojat.appmurojat.Controller;


import murojat.appmurojat.entity.User;
import murojat.appmurojat.payload.ReqLogin;
import murojat.appmurojat.payload.ResToken;
import murojat.appmurojat.security.CurrentUser;
import murojat.appmurojat.security.JwtTokenProvider;
import murojat.appmurojat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;


//    @PostMapping("/register")
//    public HttpEntity<?> registerUser(@RequestBody ReqRegister reqRegister) {
//        ApiResponse response = userService.register(reqRegister);
//        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(response);
//    }


    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody ReqLogin reqLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                reqLogin.getEmail(),
                reqLogin.getPassword()
        ));
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new ResToken(token));
    }


    @GetMapping("/userMe")
    public HttpEntity<?> userMe(@CurrentUser User user) {
        return ResponseEntity.status(user == null ? 409 : 200).body(user);
//        return ResponseEntity.status(user==null?HttpStatus.CONFLICT:HttpStatus.OK).body(user);
    }
}
