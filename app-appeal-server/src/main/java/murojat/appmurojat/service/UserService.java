package murojat.appmurojat.service;


import murojat.appmurojat.entity.ForgotPassword;
import murojat.appmurojat.entity.User;
import murojat.appmurojat.payload.ApiResponse;
import murojat.appmurojat.repository.ForgotPasswordRepository;
import murojat.appmurojat.repository.RoleRepository;
import murojat.appmurojat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    ForgotPasswordRepository forgotPasswordRepository;

    public ApiResponse generateForgotPasswordCode(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()){
            ForgotPassword forgotPassword = new ForgotPassword();
            forgotPassword.setForgotPasswordCode(generateRandom(0,999999));
            forgotPassword.setUser(byEmail.get());
            forgotPasswordRepository.save(forgotPassword);
            String message = "code for forgot password \n "+forgotPassword.getForgotPasswordCode()+" expiry time 30 minutes";
            sendMail(email,"reset password",message);
            return new ApiResponse("ok",true);
        }
        return new ApiResponse("no",false);
    }

    public static int generateRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public void sendMail(String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public UserDetails getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getUser"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException("getUser"));
    }


    //Admin
    public ApiResponse addModerator(){
        return null;
    }

}
