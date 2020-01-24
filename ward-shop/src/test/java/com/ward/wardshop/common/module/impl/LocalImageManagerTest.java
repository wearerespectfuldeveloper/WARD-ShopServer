package com.ward.wardshop.common.module.impl;

import com.ward.wardshop.common.module.ImageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class LocalImageManagerTest {

    @Autowired
    private ImageManager imageManager;

    @Test
    void uploadTest() throws IOException {
        InputStream inputStream = new FileInputStream(new File("src/main/resources/static/cat.jpg"));
        MockMultipartFile multipartFile = new MockMultipartFile("imgFile", "cat.jpg", MediaType.IMAGE_JPEG_VALUE, inputStream);
        imageManager.uploadImg(multipartFile, "product/cat_test.jpg");
    }

    @Test
    void deleteTest() throws IOException {
        imageManager.deleteImg("cat_test.jpg");
    }

    @Test
    void createFileTest() throws IOException {
        File target = new File("src/main/resources/static/product/cat-1579773147984.jpg");
        File origin = new File("src/main/resources/static/cat.jpg");

        byte[] bytes = new byte[(int) origin.length()];

        try (FileInputStream fileInputStream = new FileInputStream(origin)) {
            fileInputStream.read(bytes);

        }

        if (target.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(target)) {
                fos.write(bytes);
            }
        }
    }
}