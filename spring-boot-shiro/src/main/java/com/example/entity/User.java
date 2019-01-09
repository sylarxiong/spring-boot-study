package com.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
@Setter
@Getter
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    private String username;

    private String password;

    @ManyToMany(fetch= FetchType.EAGER,cascade = CascadeType.ALL)//立即从数据库中进行加载数据;
    @JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "uid",referencedColumnName="uid") }, inverseJoinColumns ={@JoinColumn(name = "rid",updatable=false) })
    private Set<Role> roles;// 一个用户具有多个角色
}
