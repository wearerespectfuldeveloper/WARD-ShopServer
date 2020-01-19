package com.ward.wardshop.common.module;

import java.io.IOException;

public interface ImageManager {
    void uploadImg(byte[] content, String path) throws IOException;

    void deleteImg(String path) throws IOException;
}
