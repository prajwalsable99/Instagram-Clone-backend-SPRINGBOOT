package com.prajwal.instagram.InstagramApp.dto;

import java.util.Objects;

public class UserDto {

    private int id;
    private String username;

    private String email;
    private  String name;

    private String userimage;

    public UserDto(){}
    public UserDto(int id, String username, String email, String name, String userimage) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.userimage = userimage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && Objects.equals(username, userDto.username) && Objects.equals(email, userDto.email) && Objects.equals(name, userDto.name) && Objects.equals(userimage, userDto.userimage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, name, userimage);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", userimage='" + userimage + '\'' +
                '}';
    }
}
