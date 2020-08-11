package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private String email;

    public void SetFirstName(String firstName){
        this.first_name = firstName;
    }
    public void SetLastName(String lastName) {
        this.last_name = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }

    public String getEmail() {
        return this.email;
    }
    public Integer getUser_id() { return this.user_id; }
    public String getFirst_name() { return this.first_name; }
    public String getLast_name() { return this.last_name; }
}
