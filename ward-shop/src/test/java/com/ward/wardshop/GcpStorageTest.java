package com.ward.wardshop;

import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GcpStorageTest {

    @Autowired
    private Storage storage;

    @Test
    void storageSetupTest() {
        assertNotNull(storage);
    }

    @Test
    void fileReadTest() throws IOException {
        File file = new File("src/main/resources/static/cat.jpg");

        File copy = new File("src/main/resources/static/cat_2.jpg");
        Files.copy(file.toPath(), copy.toPath());
    }
}
