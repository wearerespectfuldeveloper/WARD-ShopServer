package com.ward.wardshop.common.module.impl;

import org.apache.commons.io.FilenameUtils;

public class FilenameResolver {

   public static String generate(String filename) {
       String baseName = FilenameUtils.getBaseName(filename);
       String extension = FilenameUtils.getExtension(filename);

       return baseName
               + "-"
               + System.currentTimeMillis()
               + "."
               + extension;
   }
}
