package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.exception.CommentException;
import com.prajwal.instagram.InstagramApp.exception.PostException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Comment;

public interface CommentService {

    public Comment createComment(Comment comment,int postId,int userId) throws UserException, PostException;

    public Comment findCommentById(int commentId) throws CommentException;

    public Comment likeComment(int commentId,int userId) throws UserException,CommentException;
    public Comment unlikeComment(int commentId,int userId) throws UserException,CommentException;


}
