package com.project.bumawiki.domain.contribute.domain;

import com.project.bumawiki.domain.docs.domain.Docs;
import com.project.bumawiki.domain.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contribute {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contribute_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User contributor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_id")
    private Docs docs;
}
