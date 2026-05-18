package com.b1uffer.multisessiontest.service;

import com.b1uffer.multisessiontest.entity.Document;
import com.b1uffer.multisessiontest.repository.DocumentRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // 문서를 조회한 후 반환한다
    @PostAuthorize("returnObject.owner == authentication.name")
//    @PreAuthorize("@documentPermissionEvaluator.isOwner(#id, authentication)")
    public Document getDocument(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Document not found"));
    }

    @PreAuthorize("#document.id == authentication.name")
    public Document updateDocument(Document document, String title, String content) {
        // title도 같고, content도 같으면 return
        if(document.getTitle().equals(title) && document.getContent().equals(content)) {
            return documentRepository.save(document);
        }

        if(document.getTitle().equals(title)) {
            document.setContent(content);
            return documentRepository.save(document);
        }

        if(document.getContent().equals(content)) {
            document.setTitle(title);
            return documentRepository.save(document);
        }

        document.setTitle(title);
        document.setContent(content);
        return documentRepository.save(document);
    }
}
