package com.project.bumawiki.domain.docs.domain;

import com.project.bumawiki.domain.contribute.domain.Contribute;
import com.project.bumawiki.domain.docs.domain.type.DocsType;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Docs {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docs_id")
    private Long id;

    @Column(length = 32)
    @NotNull
    private String title;

    @Column(length = 8)
    @NotNull
    private int enroll;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VersionDocs> docsVersion = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private DocsType docsType;
    @OneToMany(mappedBy = "docs", cascade = CascadeType.ALL)
    private List<Contribute> contributor = new ArrayList<>();
    private int view = 0;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    public void setVersionDocs(List<VersionDocs> versionDocs){
        this.docsVersion = versionDocs;
    }

    public Docs updateVersionDocs(VersionDocs versionDocs){
        docsVersion.add(versionDocs);
        return this;
    }

    public void setContributor(List<Contribute> contributes){
        this.contributor =  contributes;
    }

    @Transactional
    public Docs updateContribute(Contribute contribute){
        this.contributor.add(0, contribute);
        return this;
    }

    public void increaseView(){
        this.view += 1;
    }

    public void setModifiedTime(LocalDateTime lastModifiedAt){
        this.lastModifiedAt = lastModifiedAt;
    }


}
