package com.prajwal.instagram.InstagramApp.repository;

import com.prajwal.instagram.InstagramApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

}
