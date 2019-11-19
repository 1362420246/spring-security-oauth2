package com.qbk.springsecurityoauth2server.service;


import com.qbk.springsecurityoauth2server.domain.TbUser;

public interface TbUserService {
    default TbUser getByUsername(String username) {
        return null;
    }
}
