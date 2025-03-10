package com.Online_Bakery.Controller;

import com.Online_Bakery.DTO.UserDTO;
import com.Online_Bakery.Model.User;
import com.Online_Bakery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User userDTO = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
