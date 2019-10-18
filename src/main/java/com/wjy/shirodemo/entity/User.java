package com.wjy.shirodemo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_t")
public class User implements Serializable {
    private static final long serialVersionUID = -3320971805590503443L;
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    private String salt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_t", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {
            @JoinColumn(name = "rid") })
    private List<SysRole> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public String getCredentialsSalt() {
        return username + salt + salt;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + "]";
    }

}
