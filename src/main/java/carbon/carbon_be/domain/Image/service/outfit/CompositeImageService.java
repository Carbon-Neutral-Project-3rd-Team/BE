package carbon.carbon_be.domain.Image.service.outfit;

import carbon.carbon_be.domain.Image.dto.request.CompositeImageRequestDto;
import carbon.carbon_be.domain.Image.service.resolver.CompositeKeyResolver;
import carbon.carbon_be.domain.Image.service.s3.S3ObjectChecker;
import carbon.carbon_be.domain.Image.service.s3.S3PresignService;
import carbon.carbon_be.global.s3.AppS3Props;
import carbon.carbon_be.global.s3.SpringCloudAwsProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CompositeImageService {

    private final SpringCloudAwsProps awsProps;
    private final AppS3Props appS3Props;
    private final CompositeKeyResolver resolver;
    private final S3ObjectChecker checker;
    private final S3PresignService presign;

    public String getCompositeUrl(CompositeImageRequestDto req) {
        String bucket = awsProps.getS3().getBucket();
        Duration ttl = Duration.ofSeconds(appS3Props.getPresignTtlSeconds());

        String key = resolver.resolve(
                appS3Props.getCombosPrefix(),
                req.getHat().getType(), req.getHat().getColor(),
                req.getClothes().getType(), req.getClothes().getColor(),
                req.getShoes().getType(), req.getShoes().getColor()
        );

        if (!checker.exists(bucket, key)) {
            key = appS3Props.getFallbackKey();
        }

        return presign.presignGetUrl(bucket, key, ttl);
    }
}


