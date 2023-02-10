package com.example.demo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "ENABLE")
    private boolean enable;

    @Column(name = "ROLE")
    private String role;

    public User(String username, String password) {

        super();
        this.username = username;
        this.password = password;
        this.role = "USER";
        this.enable = true;
    }

    public boolean hasRole(String roleName) {

        return this.getRole().equals(roleName);
    }

}