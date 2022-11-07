package com.tcn.meetandnote.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class FileUploadUtils {

    private static int uniqueNumber = 0;

    public static String generateFileName(MultipartFile file) {
        String[] fullFileName = file.getOriginalFilename().split("[.]");
        String extension = fullFileName[fullFileName.length - 1];
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder builder = new StringBuilder();
        builder.append(localDateTime.getDayOfYear())
                .append(localDateTime.getMonthValue())
                .append(localDateTime.getYear())
                .append(localDateTime.getHour())
                .append(localDateTime.getMinute())
                .append(localDateTime.getSecond())
                .append(localDateTime.getNano())
                .append(++uniqueNumber)
                .append("." + extension);
        return builder.toString();
    }

    public static void upload(String fileName, String path, MultipartFile fileUpload) throws IOException {
        File file = new File(path + "/" + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileUpload.getBytes());
        fos.close();
    }

    public static boolean delete(String path) {
        try {
            File file = new File(path);
            if(file.isDirectory()) {
                String[] entries = file.list();
                for(String fileName : entries) {
                    File currentFile = new File(file.getPath(), fileName);
                    currentFile.delete();
                }
            }
            return file.delete();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
