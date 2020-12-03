package honchi.api.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @SneakyThrows
    @Override
    public byte[] getImage(String imageName) {
        S3Object s3Object = s3Client.getObject(bucket, imageName);
        S3ObjectInputStream stream = s3Object.getObjectContent();

        return IOUtils.toByteArray(stream);
    }
}
