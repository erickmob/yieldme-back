package com.erickmob.yieldme.service;


import javax.servlet.http.HttpServletRequest;

import com.erickmob.yieldme.dto.UserDataDTO;
import com.erickmob.yieldme.exception.CustomException;
import com.erickmob.yieldme.jwt.JwtTokenProvider;
import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;


@Service
public class UserService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtTokenProvider.createToken(username, this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNAUTHORIZED);
        }
    }

    public String signup(UserDataDTO userDataDTO) {
        User newUser = createFromDTO(userDataDTO);
        if (!userRepository.existsByUsername(newUser.getUsername())) {
            createUser(newUser);
            walletService.createWalletForUser(newUser);
            return jwtTokenProvider.createToken(newUser.getUsername(), newUser.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private User createFromDTO(UserDataDTO userDataDTO) {
        return User.builder()
                .email(userDataDTO.getEmail())
                .nome(userDataDTO.getName())
                .username(userDataDTO.getUsername())
                .password(passwordEncoder.encode(userDataDTO.getPassword()))
                .roles(Arrays.asList( "ROLE_USER"))
                .enabled(true)
                .build();
    }

    private void createUser(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User search(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user.get();
    }
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user.get();
    }



    public String refresh() {
        User loggedUser  =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtTokenProvider.createToken(loggedUser.getUsername(), loggedUser.getRoles());
    }

    public String changePassword(String password) {
        User loggedUser  =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(loggedUser);
        return jwtTokenProvider.createToken(loggedUser.getUsername(), loggedUser.getRoles());
    }

}