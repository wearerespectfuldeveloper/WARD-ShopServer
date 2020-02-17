package com.ward.wardshop.common.module.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageManager {
    String uploadImg(MultipartFile uploadFile, String path) throws IOException;

    void deleteImg(String path) throws IOException;
}
