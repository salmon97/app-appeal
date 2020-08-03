package murojat.appmurojat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.ApiContextInitializer;

import java.beans.AppletInitializer;

@SpringBootApplication
@EnableScheduling
public class AppMurojatApplication {
    public static void main(String[] args) {
//        System.getProperties().put("proxySet", "true");
////
//        System.getProperties().put("socksProxyHost", "127.0.0.1");
////
//        System.getProperties().put("socksProxyPort", "1950");
//        ApiContextInitializer.init();
        SpringApplication.run(AppMurojatApplication.class, args);
    }

}
