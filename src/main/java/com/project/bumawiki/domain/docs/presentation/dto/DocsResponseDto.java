package com.project.bumawiki.domain.docs.presentation.dto;

import com.project.bumawiki.domain.docs.domain.Docs;
import com.project.bumawiki.domain.docs.domain.VersionDocs;
import com.project.bumawiki.domain.docs.domain.type.DocsType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DocsResponseDto {

    private Long id;

    private String title;

    private String contents;

    private DocsType docsType;
    private LocalDateTime lastModifiedAt;
    private int enroll;
    private int view;


    public DocsResponseDto(Docs docs) {
        VersionDocs versionDocs = docs.getDocsVersion().get(docs.getDocsVersion().size() - 1);
        this.id = docs.getId();
        this.title = docs.getTitle();
        this.contents = versionDocs.getContents();
        this.lastModifiedAt = docs.getLastModifiedAt();
        this.docsType = docs.getDocsType();
        this.enroll = docs.getEnroll();
        this.view = docs.getView();
    }
    public DocsResponseDto updateContent(String setContent){
        this.contents = setContent;
        return this;
    }
}

