package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "auth_data")
public class auth_data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auth_data_id;
    @Column
    private String login;
    @Column
    private String pass_hash;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private users user;
    @Column
    private String role_user;
    @Column
    private Boolean enabled;

    public void setRole_user(String role_user) { this.role_user = role_user; }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setUser(users user) { this.user = user; }
    public void setPass_hash(String pass_hash) { this.pass_hash = pass_hash; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public String getLogin() {
        return this.login;
    }
    public users getUser() {
        return this.user;
    }
    public String getPass_hash() { return this.pass_hash; }
    public String getRole_user() { return this.role_user; }
    public Boolean getEnabled() { return this.enabled; }
}
