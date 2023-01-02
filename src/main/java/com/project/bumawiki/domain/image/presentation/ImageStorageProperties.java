package com.project.bumawiki.domain.image.presentation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
public class ImageStorageProperties {

    @Value("${image.path}")
    private String path;
    public ImageStorageProperties(String path) {
        this.path = path;
    }
}
