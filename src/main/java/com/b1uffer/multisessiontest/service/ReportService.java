package com.b1uffer.multisessiontest.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    // 메서드 실행 전에 접근 권한을 확인한다
    @PreAuthorize("hasRole('ADMIN')")
    public String generateAdminReport() {
        return "Admin Report";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String generateUserReport() {
        return "User Report";
    }
}
