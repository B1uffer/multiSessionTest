package com.b1uffer.multisessiontest.service.custom;

import com.b1uffer.multisessiontest.entity.Document;
import com.b1uffer.multisessiontest.repository.DocumentRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {
    private final DocumentRepository repository;

    public CustomPermissionEvaluator(DocumentRepository repository) {
        this.repository = repository;
    }

    // 객체 기반 검사만 하는 메서드
    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject,
                                 Object permission) {
        if(targetDomainObject instanceof Document document) {
            String username = authentication.getName();
            return document.getOwner().equals(username);
        }
        return false;
    }

    // ID 기반 검사
    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {

        return false;
    }
}
