package com.Online_Bakery.Controller;


import com.Online_Bakery.Model.Cart;
import com.Online_Bakery.enums.USER_ROLE;
import com.Online_Bakery.Model.User;
import com.Online_Bakery.Repository.CartRepository;

import com.Online_Bakery.Repository.UserRepository;
import com.Online_Bakery.Requests.LoginRequest;
import com.Online_Bakery.Response.AuthResponse;
import com.Online_Bakery.Services.Impl.CustomerUserDetailsService;
import com.Online_Bakery.configurations.JwtProvider;
import com.Online_Bakery.exception.EmailAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<String> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist =  userRepository.findByEmail(user.getEmail());
        if(isEmailExist != null)
        {
            throw new EmailAlreadyExistException("Email already Exist. Please use a new email id");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(newUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepo.save(cart);

        return new ResponseEntity<>("Registration is successful", HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest)
    {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(email, password);

        Collection<?extends GrantedAuthority> authority = authentication.getAuthorities();

        String role = authority.isEmpty()?null:authority.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successful");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password)
    {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if(userDetails == null)
        {
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid password!!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
