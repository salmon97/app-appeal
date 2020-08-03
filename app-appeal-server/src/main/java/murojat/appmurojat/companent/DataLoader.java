package murojat.appmurojat.companent;

import murojat.appmurojat.entity.User;
import murojat.appmurojat.repository.RoleRepository;
import murojat.appmurojat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Value("${spring.datasource.initialization-mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            userRepository.save(
                    new User(
                            "SuperAdmin",
                            "superAdminMurojaat@gmail.com",
                            "+998931234567",
                            passwordEncoder.encode("root123"),
                            new HashSet<>(roleRepository.findAll())
                    )
            );
        }
    }
}
