package com.ashinx.fastdocs.domain.documents.bucket;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BucketService {
    public String upload(MultipartFile file, String filename) throws IOException;
    public boolean deleteFile(String fileName);
    public String generateFileName(MultipartFile file);
}
