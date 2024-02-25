package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.dto.UserDto;
import com.prajwal.instagram.InstagramApp.exception.PostException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Post;
import com.prajwal.instagram.InstagramApp.model.User;
import com.prajwal.instagram.InstagramApp.repository.PostRepository;
import com.prajwal.instagram.InstagramApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Override
    public Post createPost(Post post ,int userId) throws UserException, PostException {

        User user=userService.findUserById(userId);
        UserDto userDto =new UserDto();

        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setUserimage(user.getImage());

        post.setUser(userDto);
        post.setCreatedat(LocalDateTime.now());


        Post createdPost =postRepository.save(post);




        return createdPost;
    }

    @Override
    public String deletePost(int postId, int userId) throws UserException, PostException {

        Post post=findPostById(postId);
        User user=userService.findUserById(userId);

        if(post.getUser().getId()==user.getId()){

                postRepository.deleteById(post.getId());
                return "Post deleted successfully";
        }

        throw new PostException("You don't have access to delete post");

    }

    @Override
    public List<Post> findPostsByUserId(int userId) throws UserException {

        List<Post> posts=postRepository.findByUserId(userId);

//        if(posts.isEmpty()){
//            throw  new UserException("This user dont have any posts");
//        }

        return posts;

    }

    @Override
    public Post findPostById(int postId) throws PostException {

        Optional<Post> post=postRepository.findById(postId);
        if(post.isPresent()){
            return post.get();
        }
        throw new PostException("Post Not found with id"+String.valueOf(postId));
    }

    @Override
    public List<Post> findAllPostsByUserIds(List<Integer> userIds) throws PostException, UserException {

        List<Post> posts=postRepository.findAllPostByUserIds(userIds);

//        if(posts.isEmpty()){
//            throw  new PostException("No posts available");
//        }

        return posts;
    }

    @Override
    public String savePost(int postId, int userId) throws PostException, UserException {

        Post post=findPostById(postId);
        User user=userService.findUserById(userId);
        if(!user.getSavedPosts().contains(post)){
            user.getSavedPosts().add(post);

            userRepository.save(user);
        }

        return "post saved successfully";

    }

    @Override
    public String unsavePost(int postId, int userId) throws PostException, UserException {
        Post post=findPostById(postId);
        User user=userService.findUserById(userId);
        if(user.getSavedPosts().contains(post)){
            user.getSavedPosts().remove(post);

            userRepository.save(user);
        }

        return "post removed successfully";
    }

    @Override
    public Post likePost(int postId, int userId) throws PostException, UserException {

        Post post=findPostById(postId);
        User user=userService.findUserById(userId);

        UserDto uDto=new UserDto();

        uDto.setName(user.getName());
        uDto.setId(user.getId());
        uDto.setUsername(user.getUsername());
        uDto.setEmail(user.getEmail());
        uDto.setUserimage(user.getImage());

        post.getLikedByUsers().add(uDto);

        return postRepository.save(post);



    }

    @Override
    public Post unlikePost(int postId, int userId) throws PostException, UserException {
        Post post=findPostById(postId);
        User user=userService.findUserById(userId);

        UserDto uDto=new UserDto();

        uDto.setName(user.getName());
        uDto.setId(user.getId());
        uDto.setUsername(user.getUsername());
        uDto.setEmail(user.getEmail());
        uDto.setUserimage(user.getImage());

        post.getLikedByUsers().remove(uDto);

        return postRepository.save(post);
    }
}
