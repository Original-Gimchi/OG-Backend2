package com.project.bumawiki.domain.image.presentation;

import lombok.Getter;

import javax.persistence.Column;
import java.util.Optional;


@Getter
public class ImageDto {
    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileOriName;

    @Column(nullable = false)
    private String fileUrl;


}

