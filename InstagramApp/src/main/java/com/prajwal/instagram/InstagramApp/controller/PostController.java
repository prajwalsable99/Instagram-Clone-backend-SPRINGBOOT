package com.prajwal.instagram.InstagramApp.controller;

import com.prajwal.instagram.InstagramApp.exception.PostException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Post;
import com.prajwal.instagram.InstagramApp.model.User;
import com.prajwal.instagram.InstagramApp.repository.UserRepository;
import com.prajwal.instagram.InstagramApp.response.MessageResponse;
import com.prajwal.instagram.InstagramApp.service.PostService;
import com.prajwal.instagram.InstagramApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post,@RequestHeader("Authorization")  String token) throws PostException, UserException {

        User user=userService.findUserProfile(token);

        log.info("prajwal");
        log.info(user.getName());

        Post createPost=postService.createPost(post,user.getId());

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable("id") int id) throws PostException, UserException {


        List<Post> posts=postService.findPostsByUserId(id);


        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/following/{ids}")
    public ResponseEntity<List<Post>> findFollowingPostsByUserIdHandler(@PathVariable("ids") List<Integer> ids) throws PostException, UserException {


        List<Post> posts=postService.findAllPostsByUserIds(ids);


        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable("id") int id) throws PostException, UserException {


        Post post=postService.findPostById(id);


        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    @PutMapping("/like/{postid}")
    public ResponseEntity<Post> likePostHandler(@PathVariable("postid") int postId,@RequestHeader("Authorization")  String token) throws PostException, UserException {

        User user=userService.findUserProfile(token);

        Post post=postService.likePost(postId,user.getId());


        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    @PutMapping("/unlike/{postid}")
    public ResponseEntity<Post> unlikePostHandler(@PathVariable("postid") int postId,@RequestHeader("Authorization")  String token) throws PostException, UserException {

        User user=userService.findUserProfile(token);

        Post post=postService.unlikePost(postId,user.getId());


        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("delete/{postid}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable("postid") int postId, @RequestHeader("Authorization")  String token) throws PostException, UserException {

        User user=userService.findUserProfile(token);

//        my own logic which handles : if someinetries to delte post first it will remove the post from saved-section of all users

//        List<User> userList=userRepository.findAll();
//
//        for(User u : userList){
//            postService.unsavePost(postId, u.getId());
//        }

        String message=postService.deletePost(postId,user.getId());
        MessageResponse messageResponse=new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("savepost/{postid}")
    public ResponseEntity<MessageResponse> savePostHandler(@PathVariable("postid") int postId, @RequestHeader("Authorization")  String token) throws PostException, UserException {

        User user=userService.findUserProfile(token);

        String message=postService.savePost(postId,user.getId());
        MessageResponse messageResponse=new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("unsavepost/{postid}")
    public ResponseEntity<MessageResponse> unsavePostHandler(@PathVariable("postid") int postId, @RequestHeader("Authorization")  String token) throws PostException, UserException {

        User user=userService.findUserProfile(token);

        String message=postService.unsavePost(postId,user.getId());
        MessageResponse messageResponse=new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.ACCEPTED);
    }








}
