package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.dto.UserDto;
import com.prajwal.instagram.InstagramApp.exception.CommentException;
import com.prajwal.instagram.InstagramApp.exception.PostException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Comment;
import com.prajwal.instagram.InstagramApp.model.Post;
import com.prajwal.instagram.InstagramApp.model.User;
import com.prajwal.instagram.InstagramApp.repository.CommentRepository;
import com.prajwal.instagram.InstagramApp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, int postId, int userId) throws UserException, PostException {

        User user =userService.findUserById(userId);
        Post post=postService.findPostById(postId);

        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserimage(user.getImage());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());

        comment.setUser(userDto);

        comment.setCreatedat(LocalDateTime.now());

        Comment createdComment=commentRepository.save(comment);

        post.getComments().add(createdComment);

        postRepository.save(post);


        return createdComment;
    }

    @Override
    public Comment findCommentById(int commentId) throws CommentException {

        Optional<Comment> comment=commentRepository.findById(commentId);
        if(comment.isPresent()){
            return comment.get();
        }
        throw new CommentException("Comment does not exist with id : "+String.valueOf(commentId));
    }

    @Override
    public Comment likeComment(int commentId, int userId) throws UserException, CommentException {
        User user =userService.findUserById(userId);
        Comment comment=findCommentById(commentId);

        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserimage(user.getImage());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());

        comment.getLikedByUsers().add(userDto);


        return commentRepository.save(comment);
    }

    @Override
    public Comment unlikeComment(int commentId, int userId) throws UserException, CommentException {
        User user =userService.findUserById(userId);
        Comment comment=findCommentById(commentId);

        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserimage(user.getImage());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());

        comment.getLikedByUsers().remove(userDto);


        return commentRepository.save(comment);
    }
}
