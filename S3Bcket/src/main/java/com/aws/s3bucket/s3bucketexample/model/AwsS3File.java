package com.aws.s3bucket.s3bucketexample.model;

import com.amazonaws.services.s3.model.S3Object;

public class AwsS3File {

    private byte[] data;
    private String contentType;
    private String fileName;
    private S3Object s3Object;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public S3Object getS3Object() {
        return s3Object;
    }

    public void setS3Object(S3Object s3Object) {
        this.s3Object = s3Object;
    }
}
