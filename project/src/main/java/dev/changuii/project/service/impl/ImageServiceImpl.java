package dev.changuii.project.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import dev.changuii.project.enums.ErrorCode;
import dev.changuii.project.exception.CustomException;
import dev.changuii.project.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class ImageServiceImpl implements ImageService {

    private final static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${cloud.aws.credentials.image-url}")
    private String baseUrl;

    @Value("${server.domain}")
    private String serverDomain;



    private AmazonS3 amazonS3;

    public ImageServiceImpl(
            @Autowired AmazonS3 amazonS3
    ){
        this.amazonS3 = amazonS3;
    }


    @Override
    public String ImageUpload(MultipartFile image) throws IOException {
        return this.uploadImageToS3(image);
    }

    @Override
    public String getBasicImage() {
        return this.serverDomain+"/api/image/basic.png";
    }

    @Override
    public void deleteImage(String imageName) {
        // 기본 이미지 삭제 불가.
        if(imageName.equals("basic.png")){
            throw new CustomException(ErrorCode.S3_IMAGE_BASIC_DELETE_EXCEPTION);
        }

        try {
            this.amazonS3.deleteObject(this.bucketName, imageName);
        }catch (SdkClientException e){
            throw new CustomException(ErrorCode.S3_IMAGE_DELETE_EXCEPTION);
        }

    }

    @Override
    public byte[] imageDownload(String data) throws IOException {
        S3Object image = amazonS3.getObject(bucketName, data);
        return image.getObjectContent().readAllBytes();
    }

    @Override
    public String extractionKey(String url) {
        return url.replace(serverDomain + "/api/image/", "");
    }


    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); //image를 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);

        //S3에 요청할 때 사용할 byteInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try{
            logger.info("image 저장 실행");
            //S3로 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata);

            //실제로 S3에 이미지 데이터를 넣는 부분이다.
            amazonS3.putObject(putObjectRequest); // put image to S3
        }catch (Exception e){
            e.printStackTrace();
            logger.info("image 저장 실패");
            throw new CustomException(ErrorCode.S3_IMAGE_PUT_EXCEPTION);
        }finally {
            logger.info("image 저장 로직 종료");
            byteArrayInputStream.close();
            is.close();
        }


        // 현재는 s3 키값만 들어가있음
        String url =  serverDomain + "/api/image/" + amazonS3.getUrl(bucketName, s3FileName).toString()
                .replace(baseUrl, "");

        return url;
    }

}


