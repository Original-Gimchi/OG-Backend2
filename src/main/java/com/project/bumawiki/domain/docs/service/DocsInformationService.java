package com.project.bumawiki.domain.docs.service;

import com.project.bumawiki.domain.docs.domain.Docs;
import com.project.bumawiki.domain.docs.domain.repository.DocsRepository;
import com.project.bumawiki.domain.docs.domain.type.DocsType;
import com.project.bumawiki.domain.docs.exception.DocsNotFoundException;
import com.project.bumawiki.domain.docs.presentation.dto.DocsNameAndEnrollResponseDto;
import com.project.bumawiki.domain.docs.presentation.dto.DocsResponseDto;
import com.project.bumawiki.domain.docs.presentation.dto.VersionDocsResponseDto;
import com.project.bumawiki.domain.docs.presentation.dto.VersionResponseDto;
import com.project.bumawiki.global.annotation.ServiceWithTransactionalReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class DocsInformationService {
    private final DocsRepository docsRepository;

    public List<DocsNameAndEnrollResponseDto> findAllStudent(){
        List<Docs> allStudent = docsRepository.findByDocsType(DocsType.STUDENT);

        return allStudent.stream()
                .map(DocsNameAndEnrollResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<DocsNameAndEnrollResponseDto> findAllAccident(){
        List<Docs> allAccident = docsRepository.findByDocsType(DocsType.ACCIDENT);

        return allAccident.stream()
                .map(DocsNameAndEnrollResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<DocsNameAndEnrollResponseDto> findAllTeacher(){
        List<Docs> allTeacher = docsRepository.findByDocsType(DocsType.TEACHER);

        return allTeacher.stream()
                .map(DocsNameAndEnrollResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<DocsNameAndEnrollResponseDto> findAllMajorTeacher(){
        List<Docs> allTeacher = docsRepository.findByDocsType(DocsType.MAJOR_TEACHER);

        return allTeacher.stream()
                .map(DocsNameAndEnrollResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<DocsNameAndEnrollResponseDto> findAllMentorTeacher(){
        List<Docs> allTeacher = docsRepository.findByDocsType(DocsType.MENTOR_TEACHER);

        return allTeacher.stream()
                .map(DocsNameAndEnrollResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<DocsNameAndEnrollResponseDto> findAllClub(){
        List<Docs> findClub = docsRepository.findByDocsType(DocsType.CLUB);

        return findClub.stream()
                .map(DocsNameAndEnrollResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<DocsNameAndEnrollResponseDto> findALLFreeClub(){
        List<Docs> findFreeClub = docsRepository.findByDocsType(DocsType.FREE_CLUB);

        return findFreeClub.stream()
                .map(DocsNameAndEnrollResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DocsResponseDto> findByTitle(String title){
        List<Docs> docs = docsRepository.findAllByTitle(title);
        if(docs.size() == 0){
            throw DocsNotFoundException.EXCEPTION;
        }

        return docs.stream()
                .map(DocsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocsResponseDto findDocs(String title) {
        Docs docs = docsRepository.findByTitle(title).
                orElseThrow(() -> DocsNotFoundException.EXCEPTION);
        docs.increaseView();

        return new DocsResponseDto(docs);
    }

    public VersionResponseDto findDocsVersion(String title) {
        Docs docs = docsRepository.findByTitle(title)
                .orElseThrow(() -> DocsNotFoundException.EXCEPTION);

        List<VersionDocsResponseDto> versionDocs = docs.getDocsVersion()
                .stream()
                .map(VersionDocsResponseDto::new)
                .collect(Collectors.toList());

        return new VersionResponseDto(new DocsResponseDto(docs), versionDocs);
    }

    public List<DocsResponseDto> showDocsModifiedAtDesc(Pageable pageable){
        return docsRepository.findByLastModifiedAt(pageable)
                .stream()
                .map(DocsResponseDto::new)
                .collect(Collectors.toList());
    }


}


