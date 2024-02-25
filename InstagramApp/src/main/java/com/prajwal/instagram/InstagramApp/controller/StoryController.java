package com.prajwal.instagram.InstagramApp.controller;

import com.prajwal.instagram.InstagramApp.exception.StoryException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Story;
import com.prajwal.instagram.InstagramApp.model.User;
import com.prajwal.instagram.InstagramApp.service.StoryService;
import com.prajwal.instagram.InstagramApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    @Autowired
    private UserService userService;
    @Autowired
    private StoryService storyService;
    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story, @RequestHeader("Authorization") String token) throws UserException {
        User user=userService.findUserProfile(token);

        Story s=storyService.createStory(story,user.getId());
        return new ResponseEntity<>(s, HttpStatus.OK);


    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findUserStoriesHandler(@PathVariable("userId") int userId) throws UserException, StoryException {


        List<Story> stories=storyService.findStoriesByUserId(userId);

        return new ResponseEntity<>(stories,HttpStatus.OK);
    }
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<List<Story>>> findfollowingStoriesHandler(@PathVariable("userId") int userId) throws UserException, StoryException {


        List<List<Story>> stories=storyService.findStoriesOfFollowing(userId);

        return new ResponseEntity<>(stories,HttpStatus.OK);
    }
}
