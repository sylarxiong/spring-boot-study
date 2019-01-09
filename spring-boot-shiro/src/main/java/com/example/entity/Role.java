package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;

    private String rname;


    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="RolePermission",joinColumns={@JoinColumn(name="rid")},inverseJoinColumns={@JoinColumn(name="pid")})
    private Set<Permission> permissions;

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name="UserRole",joinColumns={@JoinColumn(name="rid")},inverseJoinColumns={@JoinColumn(name="uid")})
    @JsonIgnore
    private Set<User> users;// 一个角色对应多个用户
}
