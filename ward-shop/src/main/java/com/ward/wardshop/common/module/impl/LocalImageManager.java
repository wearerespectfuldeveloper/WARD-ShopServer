package com.ward.wardshop.common.module.impl;

import com.ward.wardshop.common.module.ImageManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Profile("local")
@Component
public class LocalImageManager implements ImageManager {

    private static final String BASE = "/Users/leafy/IdeaProjects/WARD-ShopServer/ward-shop/src/main/resources/static/";

    @Override
    public String uploadImg(MultipartFile uploadFile, String path) throws IOException {
        String filePath = BASE + path;
        File file = new File(filePath);
        uploadFile.transferTo(file);

        return "classpath:static/" + path;
    }

    @Override
    public void deleteImg(String path) throws IOException {
        File file = new File(BASE + path);
        file.delete();
    }
}
