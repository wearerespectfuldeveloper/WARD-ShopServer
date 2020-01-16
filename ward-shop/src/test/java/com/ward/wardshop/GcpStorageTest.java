package com.ward.wardshop;

import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void deleteTest() {
        storage.delete(BlobId.of("images.ward-study.com", "product/test/cat.jpg"));
    }

    @Test
    void googleStorageResourceGetTest() {
        GoogleStorageResource googleStorageResource =
                new GoogleStorageResource(storage, "gs://images.ward-study.com");
        Blob blob = googleStorageResource.getBucket().get("product/cat.jpg");

        Path path = Paths.get("src/main/resources/static/cat_test.jpg");
        blob.downloadTo(path);
    }
}
