package com.Online_Bakery.Services.Impl;

import com.Online_Bakery.Model.User;

import com.Online_Bakery.Repository.UserRepository;
import com.Online_Bakery.Services.UserService;
import com.Online_Bakery.configurations.JwtProvider;
import com.Online_Bakery.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }
}
