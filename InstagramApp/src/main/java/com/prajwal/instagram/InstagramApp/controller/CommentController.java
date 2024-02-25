package com.prajwal.instagram.InstagramApp.controller;

import com.prajwal.instagram.InstagramApp.exception.CommentException;
import com.prajwal.instagram.InstagramApp.exception.PostException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Comment;
import com.prajwal.instagram.InstagramApp.model.User;
import com.prajwal.instagram.InstagramApp.service.CommentService;
import com.prajwal.instagram.InstagramApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment,@PathVariable("postId") int postId,@RequestHeader("Authorization") String token) throws UserException, PostException {

        User user=userService.findUserProfile(token);

        Comment createdComment= commentService.createComment(comment, postId,user.getId() );

        return new ResponseEntity<>(createdComment, HttpStatus.OK);

    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<Comment> likeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable("commentId") int commentId) throws UserException, CommentException {

        User user=userService.findUserProfile(token);
        Comment comment =commentService.likeComment(commentId,user.getId());
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

    @PutMapping("/unlike/{commentId}")
    public ResponseEntity<Comment> unlikeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable("commentId") int commentId) throws UserException, CommentException {

        User user=userService.findUserProfile(token);
        Comment comment =commentService.unlikeComment(commentId,user.getId());
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }


}
