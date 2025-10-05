package carbon.carbon_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarbonBeApplication {

   public static void main(String[] args) {
      SpringApplication.run(CarbonBeApplication.class, args);
   }

}
