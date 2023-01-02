package com.project.bumawiki.domain.image.service;

import com.project.bumawiki.domain.image.exception.NoImageException;
import com.project.bumawiki.domain.image.presentation.FileStorageProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Random;

@Service
public class StorageService {
    private String uploadPath;

    private static String getRandomStr(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println("random : " + generatedString);
        return generatedString;
    }

    public ArrayList<String> saveFiles(MultipartFile[] files, String DocsName,String[] imageName ) throws IOException {
        int index = 0;
        FileStorageProperties fileStorageProperties = new FileStorageProperties();
        String randomStr = getRandomStr();
        String path = fileStorageProperties.getPath();
        ArrayList<String> fileNames = new ArrayList<>();
        for(MultipartFile file : files) {

            String filename =  DocsName+"/"+randomStr + StringUtils.cleanPath(imageName[index]);

            if(filename.length() > 30){
                filename.substring(0,30);
            }
            System.out.println("modified file name : " + filename);
            fileNames.add(filename);
            index++;
        }
        Path uploadPath = Paths.get(path+"/"+DocsName);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("make dir : " + uploadPath);
        }
        for(int i =0; i< files.length; i++) {
            try (InputStream inputStream = files[i].getInputStream()) {
                Path filePath = uploadPath.resolve(fileNames.get(i));
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileNames.get(i), ioe);
            }
        }
        return fileNames;
    }

    public String saveFile(MultipartFile file, String userName) throws IOException {

        String randomStr = getRandomStr();
        String fileName = randomStr + StringUtils.cleanPath(file.getOriginalFilename());

        Path uploadPath = Paths.get(this.uploadPath+"/"+userName);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public Resource loadFileAsResource(String DocsName, String fileName) {
        Path uploadPath = Paths.get(this.uploadPath+"/"+DocsName);
        try {
            Path filePath = uploadPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw NoImageException.EXCEPTION;
            }
        } catch (MalformedURLException ex) {
            throw NoImageException.EXCEPTION;
        }
    }
}
