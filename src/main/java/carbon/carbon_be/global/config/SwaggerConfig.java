package carbon.carbon_be.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 아직 jwt 미적용
@Configuration
public class SwaggerConfig {
   @Bean
   public OpenAPI openAPI(){
      return new OpenAPI()
          .components(new Components())
          .info(apiInfo());
   }

   private Info apiInfo(){
      return new Info()
          .title("탄소중립 API Document")
          .description("환영합니다! 탄소중립 브랜치앤바운드팀 인천 ver. 손목닥터 999" +
              "를 만들기 위한 플랫폼입니다. API를 사용하는 방법을 설명합니다.")
          .version("1.0.0");
   }
}
