package murojat.appmurojat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.ApiContextInitializer;

import java.beans.AppletInitializer;

@SpringBootApplication
@EnableScheduling
public class AppMurojatApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppMurojatApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AppMurojatApplication.class);
    }
}
