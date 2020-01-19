package com.ward.wardshop.common.module.impl;


import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.ward.wardshop.common.module.ImageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Profile("prod")
@Component
public class GcpImageManager implements ImageManager {

    private final Storage storage;
    private final String BUCKET = "images.ward-study.com";

    @Override
    public void uploadImg(byte[] content, String path) {
        storage.create(
                BlobInfo.newBuilder(BUCKET, path)
                        .setAcl(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                        .setContentType(MediaType.IMAGE_JPEG_VALUE)
                        .build(),
                content
        );
    }

    @Override
    public void deleteImg(String path) {
        storage.delete(BlobId.of(BUCKET, path));
    }
}
