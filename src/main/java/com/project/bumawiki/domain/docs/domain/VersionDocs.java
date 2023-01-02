package com.project.bumawiki.domain.docs.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Clob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionDocs {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_docs_id")
    private Long id;

    @NotNull
    private Long docsId;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String contents;

    @CreatedDate
    private LocalDateTime thisVersionCreatedAt;
}
