package com.example.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
@Entity
@Data
@Builder
@Table(name = "t_role")
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;

    private String rname;

    //private Set<Permission> permissions = new HashSet<>();

    //private Set<User> users = new HashSet<>();
}
