package com.prajwal.instagram.InstagramApp.service;

import com.prajwal.instagram.InstagramApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, BadCredentialsException {


        Optional<com.prajwal.instagram.InstagramApp.model.User> opt=userRepo.findByEmail(username);



        if(opt.isPresent()) {
            com.prajwal.instagram.InstagramApp.model.User user=opt.get();

            List<GrantedAuthority> authorities=new ArrayList<>();

//            System.out.println("errrrr ----------- "+ username);

            return new User(user.getEmail(), user.getPassword(), authorities);
        }


        throw new BadCredentialsException("Bad Credential"+ username);

    }
}
