package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.dto.UserDto;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.User;
import com.prajwal.instagram.InstagramApp.repository.UserRepository;
import com.prajwal.instagram.InstagramApp.security.JwtTokenClaims;
import com.prajwal.instagram.InstagramApp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) throws UserException {

        Optional<User> isEmailExist =userRepository.findByEmail(user.getEmail());

        if(isEmailExist.isPresent()){
            throw new UserException("Email already exists.");
        }

        Optional<User> isUsernameExist =userRepository.findByUsername(user.getUsername());

        if(isUsernameExist.isPresent()){
            throw new UserException("Username already exists.");
        }

        if(user.getEmail()==null || user.getPassword()==null ||user.getUsername()==null){
            throw new UserException("all fields are required");
        }

        User newuser= new User();
        newuser.setEmail(user.getEmail());
        newuser.setUsername(user.getUsername());
        newuser.setPassword( passwordEncoder.encode(user.getPassword()) );

        newuser.setName(user.getName());

        userRepository.save(newuser);

        return newuser;

    }

    @Override
    public User findUserById(int userId) throws UserException {

        Optional<User> opt=userRepository.findById(userId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User does not exist with id : "+ String.valueOf(userId));
    }


    // yet to implement--------------------------------implemnetd later->done
    @Override
    public User findUserProfile(String token) throws UserException {
        token=token.substring(7);
        JwtTokenClaims jwtTokenClaims=jwtTokenProvider.getClaimsFromToken(token);

        String username=jwtTokenClaims.getUsername();
        Optional<User> opt=userRepository.findByEmail(username);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("Invalid Token");




    }

    //-----------------------------------------------

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> opt=userRepository.findByUsername(username);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User does not exist with name : "+ username);

    }

    @Override
    public String followUser(int reqUserId, int followUserId) throws UserException {

        User reqUser =findUserById(reqUserId);
        User followUser =findUserById(followUserId);

        UserDto follower=new UserDto();

        follower.setId(reqUser.getId());
        follower.setUsername(reqUser.getUsername());
        follower.setEmail(reqUser.getEmail());
        follower.setName(reqUser.getName());
        follower.setUserimage(reqUser.getImage());

        UserDto following=new UserDto();

        following.setId(followUser.getId());
        following.setUsername(followUser.getUsername());
        following.setName(followUser.getName());
        following.setEmail(followUser.getEmail());
        following.setUserimage(followUser.getImage());


        reqUser.getFollowing().add(following);
        followUser.getFollowers().add(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);




        return "you are following user :"+followUser.getUsername();
    }

    @Override
    public String unFollowUser(int reqUserId, int followUserId) throws UserException {
        User reqUser =findUserById(reqUserId);
        User followUser =findUserById(followUserId);

        UserDto follower=new UserDto();

        follower.setId(reqUser.getId());
        follower.setUsername(reqUser.getUsername());
        follower.setEmail(reqUser.getEmail());
        follower.setName(reqUser.getName());
        follower.setUserimage(reqUser.getImage());

        UserDto following=new UserDto();

        following.setId(followUser.getId());
        following.setUsername(followUser.getUsername());
        following.setName(followUser.getName());
        following.setEmail(followUser.getEmail());
        following.setUserimage(followUser.getImage());


        reqUser.getFollowing().remove(following);
        followUser.getFollowers().remove(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "you have unfollowed user :"+followUser.getUsername();
    }

    @Override
    public List<User> findUsersByIds(List<Integer> userIds) throws UserException {

        List<User> users=userRepository.findAllUsersByUserIds(userIds);
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {

        List<User>users=userRepository.findByQuery(query);
//        if(users.isEmpty())throw  new UserException("users not found");

        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {

        if(updatedUser.getEmail()!=null){
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getBio()!=null){
            existingUser.setBio(updatedUser.getBio());
        }
        if(updatedUser.getName()!=null){
            existingUser.setName(updatedUser.getName());
        }
        if(updatedUser.getUsername()!=null){
            existingUser.setUsername(updatedUser.getUsername());
        }
        if(updatedUser.getMobile()!=null){
            existingUser.setMobile(updatedUser.getMobile());
        }
        if(updatedUser.getGender()!=null){
            existingUser.setGender(updatedUser.getGender());
        }
        if(updatedUser.getImage()!=null){
            existingUser.setImage(updatedUser.getImage());

        }

        if(updatedUser.getId()==existingUser.getId()){
            userRepository.save(existingUser);
            return existingUser;
        }else {
            throw new UserException("you cant update this user");
        }





    }
}
