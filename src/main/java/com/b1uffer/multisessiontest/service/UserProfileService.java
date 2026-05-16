package com.b1uffer.multisessiontest.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @PreAuthorize("#username == authentication.name")
    public String getUserProfile(String username) {
        return "userProfile : " + username;
    }
}
