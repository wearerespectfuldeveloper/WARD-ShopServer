package com.ward.wardshop;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Test
    void uploadTest() throws IOException {
        File file = new File("src/main/resources/static/cat.jpg");

        List<Acl> acls = new ArrayList<>();
        acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        Blob blob = storage.create(
                BlobInfo.newBuilder(
                        "images.ward-study.com",
                        "product/" + file.getName()
                ).setAcl(acls).setContentType("image/jpeg").build(),
                Files.readAllBytes(file.toPath())
        );
    }
}
