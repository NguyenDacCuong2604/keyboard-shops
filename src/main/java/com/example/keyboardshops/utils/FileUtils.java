package com.example.keyboardshops.utils;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 * @author  Dac Cuong
 * @since 07/10/2024
 */
public class FileUtils {
    public static String randomNameFile(String fileName){
        Objects.requireNonNull(fileName);
        String name = UUID.randomUUID().toString();
        if(fileName.contains(".")){
            String extension = fileName.substring(fileName.lastIndexOf("."));
            return name+extension;
        }
        return name;

    }
    public static String randomNameFile(File fileName){
      return randomNameFile(fileName.getName());
    }
}
