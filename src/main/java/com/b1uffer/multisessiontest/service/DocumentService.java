package com.b1uffer.multisessiontest.service;

import com.b1uffer.multisessiontest.entity.Document;
import com.b1uffer.multisessiontest.repository.DocumentRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // 문서를 조회한 후 반환한다
    @PostAuthorize("returnObject.owner == authentication.name")
    public Document getDocument(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Document not found"));
    }

    public Document updateDocument(Document document) {

    }
}
