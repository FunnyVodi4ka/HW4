package myappuserservice.springmyapp.repository;

import java.util.Date;

public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private int age;
    private Date created_at;

    // Конструкторы, геттеры и сеттеры

    public UserDTO() {this.created_at = new java.util.Date();}

    public UserDTO(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.created_at = new java.util.Date();
    }

    public UserDTO(Long userId, String name, String email, int age) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.created_at = new java.util.Date();
    }

    public Long getUser_id() {
        return userId;
    }

    public void setUser_id(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}

