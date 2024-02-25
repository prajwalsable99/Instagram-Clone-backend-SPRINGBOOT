package com.prajwal.instagram.InstagramApp.repository;

import com.prajwal.instagram.InstagramApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {


    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);

    // manually write function logic

    @Query("Select u from User u Where u.id in :givenlist ")
    public List<User> findAllUsersByUserIds(@Param("givenlist") List<Integer> userIds);

    @Query("Select distinct u from User u where u.username like %:q% OR u.email like %:q%  ")
    public List<User> findByQuery(@Param("q") String query);




}
