package carbon.carbon_be.global.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.cloud.aws")
public class SpringCloudAwsProps {

    private Credentials credentials = new Credentials();
    private Region region = new Region();
    private S3 s3 = new S3();

    @Data
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

    @Data
    public static class Region {
        private String statik; // region.static 바인딩용 우회 필드
        // yml 키가 static이므로 setter/getter를 아래처럼 맞춰줍니다.
        public String getStatic() { return statik; }
        public void setStatic(String v) { this.statik = v; }
    }

    @Data
    public static class S3 {
        private String bucket;
    }
}


