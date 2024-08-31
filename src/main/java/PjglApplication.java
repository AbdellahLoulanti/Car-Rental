
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.PjGl.pjgl", "Chain"})
public class PjglApplication {

    public static void main(String[] args) {
        SpringApplication.run(PjglApplication.class, args);
    }
}
