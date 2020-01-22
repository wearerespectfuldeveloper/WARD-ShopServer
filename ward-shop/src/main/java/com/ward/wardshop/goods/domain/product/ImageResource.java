package com.ward.wardshop.goods.domain.product;

import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Embeddable
public class ImageResource {
    private String imagePath;

    private static final String BASE_PATH = "product/";

    private ImageResource(String fileName) {
        this.imagePath = fileName;
    }
    public static ImageResource generateFileName(String fileName) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_PATH)
                .append(FilenameUtils.getBaseName(fileName))
                .append("-")
                .append(LocalDateTime.now())
                .append(FilenameUtils.getExtension(fileName));

        return new ImageResource(builder.toString());
    }
}
