package com.aws.s3bucket.s3bucketexample.utill;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.aws.s3bucket.s3bucketexample.model.AwsS3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

@Service
public class AwsS3Connector {

    private final AmazonS3 amazonS3;

    @Autowired
    public AwsS3Connector(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public boolean uploadMultipartFilesToAwsS3(String bucketName, String objectKey, MultipartFile multipartFile) {
        boolean status = false;
        try {
            System.out.println("inside upload method to awss3::::::::");
            File file = this.convertMultiPartToFile(multipartFile);
            this.amazonS3.putObject(bucketName, objectKey, file);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public AwsS3File loadFileDataFromS3(String bucketName, String objectKey) {
        AwsS3File awsS3File = new AwsS3File();
        try {
            S3Object s3Object = this.amazonS3.getObject(bucketName, objectKey);
            InputStream inputStream = s3Object.getObjectContent();
            awsS3File.setS3Object(s3Object);
            awsS3File.setContentType(s3Object.getObjectMetadata().getContentType());
            awsS3File.setData(IOUtils.toByteArray(inputStream));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return awsS3File;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

}
