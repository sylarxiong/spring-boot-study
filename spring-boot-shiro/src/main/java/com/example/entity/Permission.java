package com.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "permission")
@EntityListeners(AuditingEntityListener.class)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    private String name;

    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="RolePermission",joinColumns={@JoinColumn(name="pid")},inverseJoinColumns={@JoinColumn(name="rid")})
    private Set<Role> roles;
}
