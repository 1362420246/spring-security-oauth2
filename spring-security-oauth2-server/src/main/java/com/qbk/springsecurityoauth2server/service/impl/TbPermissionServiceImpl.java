package com.qbk.springsecurityoauth2server.service.impl;


import com.qbk.springsecurityoauth2server.domain.TbPermission;
import com.qbk.springsecurityoauth2server.mapper.TbPermissionMapper;
import com.qbk.springsecurityoauth2server.service.TbPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbPermissionServiceImpl implements TbPermissionService {

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    public List<TbPermission> selectByUserId(Long userId) {
        return tbPermissionMapper.selectByUserId(userId);
    }
}
