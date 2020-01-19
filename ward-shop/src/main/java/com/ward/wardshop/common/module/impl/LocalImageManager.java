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
        File file = new File(BASE + path);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content);
    }

    @Override
    public void deleteImg(String path) throws IOException {
        File file = new File(BASE + path);
        file.delete();
    }
}
