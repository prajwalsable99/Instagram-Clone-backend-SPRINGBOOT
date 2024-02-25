package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.User;

import java.util.List;

public interface UserService {

    public User registerUser (User user) throws UserException;

    public User findUserById(int useId) throws UserException;

    public User findUserProfile(String token) throws UserException;

    public User findUserByUsername(String username) throws UserException;

    public String followUser(int reqUserId,int followUserUd) throws  UserException;
    public String unFollowUser(int reqUserId,int followUserUd) throws  UserException;

    public List<User> findUsersByIds(List<Integer> userIds) throws  UserException;
    public List<User> searchUser(String query) throws  UserException;

    public User updateUserDetails(User updatedUser,User existingUser) throws UserException;





}
