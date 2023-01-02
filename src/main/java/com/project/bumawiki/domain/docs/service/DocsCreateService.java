package com.project.bumawiki.domain.docs.service;

import com.project.bumawiki.domain.auth.domain.repository.AuthIdRepository;
import com.project.bumawiki.domain.contribute.domain.Contribute;
import com.project.bumawiki.domain.docs.domain.Docs;
import com.project.bumawiki.domain.docs.domain.VersionDocs;
import com.project.bumawiki.domain.docs.domain.repository.DocsRepository;
import com.project.bumawiki.domain.docs.domain.repository.VersionDocsRepository;
import com.project.bumawiki.domain.image.service.ImageService;
import com.project.bumawiki.domain.user.entity.User;
import com.project.bumawiki.domain.user.exception.UserNotFoundException;
import com.project.bumawiki.domain.user.exception.UserNotLoginException;
import com.project.bumawiki.global.jwt.config.JwtConstants;
import com.project.bumawiki.global.jwt.util.JwtUtil;
import com.project.bumawiki.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.bumawiki.domain.docs.presentation.dto.DocsResponseDto;
import com.project.bumawiki.domain.docs.presentation.dto.DocsCreateRequestDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocsCreateService {
    private final DocsRepository docsRepository;
    private final VersionDocsRepository versionDocsRepository;

    @Autowired
    private final StorageService storageService;
    private final JwtUtil jwtUtil;
    private final AuthIdRepository authIdRepository;
    private final ImageService imageService;


    @Transactional

    public DocsResponseDto execute(DocsCreateRequestDto docsCreateRequestDto, String bearer, MultipartFile[] files) throws IOException {

        checkIsLoginUser(bearer);
        setImageUrlInContents(docsCreateRequestDto,imageService.GetFileUrl(files, docsCreateRequestDto.getTitle()));


        Docs docs = createDocs(docsCreateRequestDto);
        VersionDocs savedDocs = saveVersionDocs(docsCreateRequestDto, docs.getId());
        docs.updateVersionDocs(savedDocs);

        setContribute(docs);
        List<VersionDocs> versionDocs = new ArrayList<>();
        versionDocs.add(savedDocs);

        docs.setVersionDocs(versionDocs);

        DocsResponseDto docsResponseDto = new DocsResponseDto(docs);
        return docsResponseDto;
    }


    public void checkIsLoginUser(String bearer){
        String authId = jwtUtil.getJwtBody(bearer).get(JwtConstants.AUTH_ID.message).toString();

        authIdRepository.findByAuthId(authId)
                .orElseThrow(() -> UserNotLoginException.EXCEPTION);
    }
    @Transactional
    private VersionDocs saveVersionDocs(DocsCreateRequestDto docsCreateRequestDto, Long id){
        VersionDocs savedDocs = versionDocsRepository.save(
                VersionDocs.builder()
                        .docsId(id)
                        .contents(docsCreateRequestDto.getContents())
                        .thisVersionCreatedAt(LocalDateTime.now())
                        .build()
        );
        return savedDocs;
    }

    @Transactional
    private void setContribute(Docs docs) {
        User user = SecurityUtil.getCurrentUser().getUser();
        if(user == null){
            throw UserNotFoundException.EXCEPTION;
        }
        Contribute contribute = Contribute.builder()
                .docs(docs)
                .contributor(user)
                .build();
        ArrayList<Contribute> contributes = new ArrayList<>();
        contributes.add(contribute);
        docs.setContributor(contributes);
        user.setContributeDocs(contributes);
    }

    @Transactional
    private Docs createDocs(DocsCreateRequestDto docsCreateRequestDto) {
        return docsRepository.save(
                Docs.builder()
                        .title(docsCreateRequestDto.getTitle())
                        .enroll(docsCreateRequestDto.getEnroll())
                        .docsType(docsCreateRequestDto.getDocsType())
                        .lastModifiedAt(LocalDateTime.now())
                        .build()
        );
    }


    public void setImageUrlInContents(DocsCreateRequestDto docsCreateRequestDto,ArrayList<String> urls){
        String content = docsCreateRequestDto.getContents();
        for (String url : urls) {
            content = content.replace("[[사진]]",url);
        }
        docsCreateRequestDto.updateContent(content);

    }
}


