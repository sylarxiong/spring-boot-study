package com.example.dao;

import com.example.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    @Query(nativeQuery = true,value = "select p.id,p.permission_name,p.permission_code,p.menu_code,p.menu_name from role_permission rp left join permission p on(rp.permission_id = p.id) where rp.role_id = :roleId")
    List<Permission> findByRoleId(@Param("roleId") Integer roleId);
}
