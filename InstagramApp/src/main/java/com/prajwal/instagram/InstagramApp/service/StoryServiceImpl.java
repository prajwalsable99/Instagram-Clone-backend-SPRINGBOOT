package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.dto.UserDto;
import com.prajwal.instagram.InstagramApp.exception.StoryException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Story;
import com.prajwal.instagram.InstagramApp.model.User;
import com.prajwal.instagram.InstagramApp.repository.StoryRepository;
import com.prajwal.instagram.InstagramApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService{
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public Story createStory(Story story, int userId) throws UserException {

        User user=userService.findUserById(userId);

        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserimage(user.getImage());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());

        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());

        user.getStories().add(story);

//        no need because in model cascade=all
//        userRepository.save(user);



       return storyRepository.save(story);



    }

    @Override
    public List<Story> findStoriesByUserId(int userId) throws UserException, StoryException {
        User user=userService.findUserById(userId);

        List<Story> stories=user.getStories();


        return stories;

    }

    @Override
    public List<List<Story>> findStoriesOfFollowing(int userId) throws UserException, StoryException {

        User curruser=userService.findUserById(userId);


        List<List<Story>> storiesOfFollowing = new ArrayList<>();
        for (UserDto u :curruser.getFollowing()){
            User user=userService.findUserById(u.getId());

            List<Story> stories=user.getStories();


            if(!stories.isEmpty()){

                storiesOfFollowing.add(stories);
            }

        }
        return  storiesOfFollowing;
    }


}
