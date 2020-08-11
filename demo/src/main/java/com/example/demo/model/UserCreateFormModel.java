package com.example.demo.model;

public class UserCreateFormModel {
    private String first_name;
    private String last_name;
    private String email;
    private String role;

    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }

    public String getFirst_name() { return this.first_name; }
    public String getLast_name() { return this.last_name; }
    public String getEmail() { return this.email; }
    public String getRole() { return this.role; }
}
