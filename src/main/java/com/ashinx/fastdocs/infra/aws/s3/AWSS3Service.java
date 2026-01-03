package com.ashinx.fastdocs.infra.aws.s3;

import com.ashinx.fastdocs.domain.documents.bucket.BucketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AWSS3Service implements BucketService {
    private final S3Client s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public AWSS3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String upload(MultipartFile file, String fileName) throws IOException {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            PutObjectResponse response = s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(file.getBytes())
            );

            System.out.println("File uploaded successfully. ETag: " + response.eTag());
            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;

        } catch (S3Exception e) {
            throw new RuntimeException("Failed to upload file to S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public boolean deleteFile(String fileName) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            System.out.println("File deleted successfully: " + fileName);
            return true;

        } catch (S3Exception e) {
            System.err.println("Failed to delete file: " + e.awsErrorDetails().errorMessage());
            return false;
        }
    }

    public String generateFileName(MultipartFile file) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String originalFileName = file.getOriginalFilename();

        return timestamp + "_" + uuid + "_" + originalFileName;
    }
}
