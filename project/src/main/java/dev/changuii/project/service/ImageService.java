package dev.changuii.project.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {


    public String ImageUpload(MultipartFile image) throws IOException;
    public String getBasicImage();
    public void deleteImage(String imageName);
    public byte[] imageDownload(String data) throws IOException;
    public String extractionKey(String url);
}
