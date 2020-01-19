package com.ward.wardshop.common.module.impl;

import com.ward.wardshop.common.module.ImageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class LocalImageManagerTest {

    @Autowired
    private ImageManager imageManager;

    @Test
    void uploadTest() throws IOException {
        File file = new File("src/main/resources/static/cat.jpg");
        byte[] bytes = new byte[(int) file.length()];

        FileInputStream fileInputStream = new FileInputStream(file);

        fileInputStream.read(bytes);

        imageManager.uploadImg(bytes, "cat_test.jpg");
    }

    @Test
    void deleteTest() throws IOException {
        imageManager.deleteImg("cat_test.jpg");
    }
}