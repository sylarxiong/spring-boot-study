package com.example.service;

import com.example.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> getPermissionByRid(Integer rid);
}
