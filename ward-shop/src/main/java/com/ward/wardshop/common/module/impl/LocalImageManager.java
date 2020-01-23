package com.ward.wardshop.common.module.impl;

import com.ward.wardshop.common.module.ImageManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Profile("local")
@Component
public class LocalImageManager implements ImageManager {

    private static final String BASE = "src/main/resources/static/";

    @Override
    public void uploadImg(byte[] content, String path) throws IOException {
        String filePath = BASE + path;
        File file = new File(filePath);

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(content);
            }
        } else {
            throw new IOException("Fail to create file");
        }
    }

    @Override
    public void deleteImg(String path) throws IOException {
        File file = new File(BASE + path);
        file.delete();
    }
}
