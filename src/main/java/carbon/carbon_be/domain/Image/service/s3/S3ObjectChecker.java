package carbon.carbon_be.domain.Image.service.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
public class S3ObjectChecker {
    private final S3Client s3;

    public boolean exists(String bucket, String key) {
        try {
            s3.headObject(HeadObjectRequest.builder().bucket(bucket).key(key).build());
            return true;
        } catch (S3Exception e) {
            return false; // 404 포함 전부 false 처리(원하면 statusCode로 분기)
        }
    }
}

