package carbon.carbon_be.global.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.s3")
public class AppS3Props {
    private String combosPrefix;
    private long presignTtlSeconds;
    private String fallbackKey;
}

