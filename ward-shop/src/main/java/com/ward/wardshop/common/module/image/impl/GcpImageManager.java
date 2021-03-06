package com.ward.wardshop.common.module.image.impl;


import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.ward.wardshop.common.module.image.ImageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Profile("prod")
@Component
public class GcpImageManager implements ImageManager {

    private final Storage storage;
    private final String PROTOCOL = "http://";
    private final String BUCKET = "images.ward-study.com";

    @Override
    public String uploadImg(MultipartFile uploadFile, String path) throws IOException {
        storage.create(
                BlobInfo.newBuilder(BUCKET, path)
                        .setAcl(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                        .setContentType(MediaType.IMAGE_JPEG_VALUE)
                        .build(),
                uploadFile.getBytes()
        );

        return PROTOCOL + BUCKET + "/" + path;
    }

    @Override
    public void deleteImg(String path) {
        String resourceDir = extractResourceDir(path);
        storage.delete(BlobId.of(BUCKET, resourceDir));
    }

    private String extractResourceDir(String bucketPath) {
        return bucketPath.replace(PROTOCOL + BUCKET + "/", "");
    }
}
