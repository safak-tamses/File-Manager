package com.example.etstur_java_backend.entity.dao;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class FileModelResponseDAO {
    private String fileName;
    private String fileExtension;
    private String path;
    private long fileSize;
    private Date createDate;
    private Date updateDate;
    private String userName;

}
