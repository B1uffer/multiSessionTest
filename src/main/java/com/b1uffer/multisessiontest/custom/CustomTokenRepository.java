package com.b1uffer.multisessiontest.custom;

import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import java.util.Date;

public class CustomTokenRepository extends JdbcTokenRepositoryImpl {
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        super.updateToken(series, tokenValue, lastUsed);
        // 커스터마이징 로직, 로그 기록 추가 등
        System.out.println("Token updated for series : " + series + " and tokenValue : " + tokenValue);
    }
}
