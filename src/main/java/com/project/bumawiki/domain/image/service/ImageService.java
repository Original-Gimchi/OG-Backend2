package com.project.bumawiki.domain.image.service;

import com.project.bumawiki.domain.image.exception.NoImageException;
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
public class ImageService {
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

    public String saveFile(MultipartFile file, String userName) throws IOException {

        String randomStr = getRandomStr();
        String fileName = randomStr + StringUtils.cleanPath(file.getOriginalFilename());

        Path uploadPath = Paths.get("C:\\Users\\KHH\\Desktop\\Image\\"+userName);
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
    public ArrayList<String> GetFileUrl(MultipartFile[] files, String DocsName) throws IOException {
        ArrayList<String> ImageUrl = new ArrayList<>();
        int i=0;
        for (MultipartFile file : files){
            String fileName = saveFile(file,DocsName);
            ImageUrl.add("<<http://10.150.150.56/image/display/"+DocsName+"/"+fileName+">>");
            i++;
        }
        return ImageUrl;
    }

    public Resource loadFileAsResource(String DocsName, String fileName) {
        Path uploadPath = Paths.get("C:\\Users\\KHH\\Desktop\\Image\\"+DocsName);
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
