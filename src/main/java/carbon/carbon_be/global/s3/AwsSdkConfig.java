package carbon.carbon_be.global.s3;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@EnableConfigurationProperties({SpringCloudAwsProps.class, AppS3Props.class})
public class AwsSdkConfig {

    @Bean
    public StaticCredentialsProvider staticCredentialsProvider(SpringCloudAwsProps props) {
        AwsBasicCredentials creds = AwsBasicCredentials.create(
                props.getCredentials().getAccessKey(),
                props.getCredentials().getSecretKey()
        );
        return StaticCredentialsProvider.create(creds);
    }

    @Bean
    public S3Client s3Client(SpringCloudAwsProps props, StaticCredentialsProvider cp) {
        return S3Client.builder()
                .region(Region.of(props.getRegion().getStatic()))
                .credentialsProvider(cp)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(SpringCloudAwsProps props, StaticCredentialsProvider cp) {
        return S3Presigner.builder()
                .region(Region.of(props.getRegion().getStatic()))
                .credentialsProvider(cp)
                .build();
    }
}

