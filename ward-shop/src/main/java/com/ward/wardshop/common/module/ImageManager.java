package com.ward.wardshop.common.module;

public interface ImageManager {
    void uploadImg(byte[] content, String path);

    void deleteImg(String path);
}
