package com.prajwal.instagram.InstagramApp.controller;

import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.User;
import com.prajwal.instagram.InstagramApp.response.MessageResponse;
import com.prajwal.instagram.InstagramApp.service.UserService;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/id/{fid}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable int fid) throws UserException {
        User user = userService.findUserById(fid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{uname}")
    public ResponseEntity<User> findUserByUsernameHandler(@PathVariable String uname) throws UserException {
        User user = userService.findUserByUsername(uname);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/follow/{followuserid}")
    public ResponseEntity<MessageResponse> followHandler(@PathVariable("followuserid") int followuserid ,@RequestHeader("Authorization") String token) throws UserException {

            User requser =userService.findUserProfile(token);
        String res= userService.followUser(requser.getId(),followuserid);

        return new ResponseEntity<>(new MessageResponse(res), HttpStatus.OK);


    }

    @PutMapping("/unfollow/{followuserid}")
    public ResponseEntity<MessageResponse> unfollowHandler(@PathVariable int followuserid,@RequestHeader("Authorization") String token) throws UserException {

        User requser =userService.findUserProfile(token);

        String res= userService.unFollowUser(requser.getId(),followuserid);

        return new ResponseEntity<>(new MessageResponse(res), HttpStatus.OK);


    }

    @GetMapping("/req")
    public ResponseEntity<User> finsUserProfile(@RequestHeader("Authorization") String token) throws UserException {

        User user=userService.findUserProfile(token);

        return new ResponseEntity<>(user,HttpStatus.OK);

    }


    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> findUsersbyIdsHandler(@PathVariable List<Integer> userIds ) throws UserException {
        List<User> users= userService.findUsersByIds(userIds);
      return new ResponseEntity<List<User>>(users,HttpStatus.OK);
//        return null;
    }

//    api/users/search?q="prajwal123"
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchHandler(@RequestParam("q") String query ) throws UserException {

        List<User> users= userService.searchUser(query);
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    @PutMapping("/account/edit")
    public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization") String token,@RequestBody User updateduser) throws UserException {

        User existuser =userService.findUserProfile(token);

        User updatedUser=userService.updateUserDetails(updateduser,existuser);

        return new ResponseEntity<>(updatedUser,HttpStatus.OK);

    }








}
