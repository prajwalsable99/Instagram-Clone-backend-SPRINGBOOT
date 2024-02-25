package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.exception.PostException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Post;

import java.util.List;

public interface PostService {

    public Post createPost(Post post,int userId) throws UserException, PostException;
    public String deletePost(int postId,int userId) throws UserException,PostException;

    public List<Post> findPostsByUserId(int userId) throws UserException;

    public Post findPostById(int postId) throws PostException;

    public List<Post> findAllPostsByUserIds(List<Integer> userIds) throws PostException,UserException;

    public String savePost(int postId,int userId) throws PostException,UserException;
    public String unsavePost(int postId,int userId) throws PostException,UserException;
    public Post likePost(int postId,int userId) throws PostException,UserException;
    public Post unlikePost(int postId,int userId) throws PostException,UserException;




}
