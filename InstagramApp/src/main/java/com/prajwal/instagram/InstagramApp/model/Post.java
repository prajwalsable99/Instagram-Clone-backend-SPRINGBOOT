package com.prajwal.instagram.InstagramApp.model;

import com.prajwal.instagram.InstagramApp.dto.UserDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String caption;
    private String location;

    private String image;

    private LocalDateTime createdat;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))

    })
    private UserDto user;

    @OneToMany
    private List<Comment> comments= new ArrayList<>();

    @Embedded
    @ElementCollection
    @JoinTable(name = "likedByUsers",joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserDto> likedByUsers =new HashSet<>();

    public Post(){}

    public Post(int id, String caption, String location, String image, LocalDateTime createdat, UserDto user, List<Comment> comments, Set<UserDto> likedByUsers) {
        this.id = id;
        this.caption = caption;
        this.location = location;
        this.image = image;
        this.createdat = createdat;
        this.user = user;
        this.comments = comments;
        this.likedByUsers = likedByUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<UserDto> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<UserDto> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                ", createdat=" + createdat +
                ", user=" + user +
                ", comments=" + comments +
                ", likedByUsers=" + likedByUsers +
                '}';
    }
}
