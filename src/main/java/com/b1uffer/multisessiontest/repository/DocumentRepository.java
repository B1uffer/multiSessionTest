package com.b1uffer.multisessiontest.repository;

import com.b1uffer.multisessiontest.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findById(Long id);
}
