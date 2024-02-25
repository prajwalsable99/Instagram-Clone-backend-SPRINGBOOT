package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.exception.StoryException;
import com.prajwal.instagram.InstagramApp.exception.UserException;
import com.prajwal.instagram.InstagramApp.model.Story;

import java.util.List;

public interface StoryService {

    public Story createStory(Story story,int userId) throws UserException;
    public List<Story> findStoriesByUserId( int userId) throws UserException, StoryException;
    public List<List<Story>> findStoriesOfFollowing(int userId) throws UserException, StoryException;

}
