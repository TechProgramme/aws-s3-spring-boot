package com.aws.s3bucket.s3bucketexample.controller;

import com.aws.s3bucket.s3bucketexample.model.AwsS3File;
import com.aws.s3bucket.s3bucketexample.model.ResponseFile;
import com.aws.s3bucket.s3bucketexample.utill.AwsS3Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RestController
@RequestMapping("awsS3controller")
public class AWSS3Controller {

    private final AwsS3Connector awsS3Connector;

    @Autowired
    public AWSS3Controller(AwsS3Connector awsS3Connector) {
        this.awsS3Connector = awsS3Connector;
    }

    @PostMapping("upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("imageName") String imageName,
                                 @RequestParam("folderName") String folderName) {
        ResponseEntity responseEntity;

            boolean status = this.awsS3Connector.uploadMultipartFilesToAwsS3("test_bucket" + "/" + folderName.trim(), imageName, file);
            if (status) {
                responseEntity = new ResponseEntity<>( HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        return responseEntity;
    }

    @GetMapping("getFile")
    public ResponseFile getFile(@RequestParam("imageName") String imageName,
                                @RequestParam("folderName") String folderName) {
        ResponseFile responseFile = new ResponseFile();
        try {

                AwsS3File awsS3File = this.awsS3Connector.loadFileDataFromS3("test_bucket" + "/" + folderName.trim(), imageName);

                if (awsS3File.getData() != null) {
                    responseFile.setBase64File(Base64.getEncoder().encodeToString(awsS3File.getData()));
                    responseFile.setContentType(awsS3File.getContentType());
                } else {
                    System.out.println("imageName:" + imageName + " folderName:" + folderName + " data not found");
                }

        } catch (NullPointerException e) {
            System.out.println("BucketName:" + folderName + "  imageName:" + imageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseFile;
    }

}
