package com.prajwal.instagram.InstagramApp.model;

import com.prajwal.instagram.InstagramApp.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))

    })
    private UserDto user;

    @NotNull
    private String storyimage;

    private LocalDateTime timestamp;

    public  Story(){}

    public Story(int id, UserDto user, String storyimage, LocalDateTime timestamp) {
        this.id = id;
        this.user = user;
        this.storyimage = storyimage;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getStoryimage() {
        return storyimage;
    }

    public void setStoryimage(String storyimage) {
        this.storyimage = storyimage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
