package com.qbk.springsecurityoauth2server.service;


import com.qbk.springsecurityoauth2server.domain.TbPermission;

import java.util.List;

public interface TbPermissionService {
    default List<TbPermission> selectByUserId(Long userId) {
        return null;
    }
}
