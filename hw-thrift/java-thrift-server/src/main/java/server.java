
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class server {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(server.class);
        application.run(args);
    }
}
