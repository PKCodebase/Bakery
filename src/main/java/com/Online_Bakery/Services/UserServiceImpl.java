package com.Online_Bakery.Services;

import com.Online_Bakery.Model.UserEntity;

import com.Online_Bakery.Repository.UserRepository;
import com.Online_Bakery.configurations.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public UserEntity findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        UserEntity user = userRepository.findByEmail(email);
        if(user == null) {
            throw new Exception("User not found");
        }

        return user;
    }
}
