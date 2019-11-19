package com.qbk.springsecurityoauth2server.service.impl;

import com.qbk.springsecurityoauth2server.mapper.TbRoleMapper;
import com.qbk.springsecurityoauth2server.service.TbRoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class TbRoleServiceImpl implements TbRoleService {

    @Resource
    private TbRoleMapper tbRoleMapper;

}
