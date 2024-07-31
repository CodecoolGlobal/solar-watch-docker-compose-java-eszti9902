package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.UserAlreadyExistsException;
import com.codecool.solarwatch.model.Role;
import com.codecool.solarwatch.model.UserEntity;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = contextUser.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(format("could not find user %s in the repository", username)));

    }

    public void createUser(UserEntity user) {
        String username = user.getUsername();
        Optional<UserEntity> userFromDB = userRepository.findByUsername(username);
        if (userFromDB.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());
        userRepository.save(newUser);
    }

    public ResponseEntity<Void> promoteUserToAdmin(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            user.get().setRole(Role.ROLE_ADMIN);
            userRepository.save(user.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Void> unPromoteUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            user.get().setRole(Role.ROLE_ADMIN);
            userRepository.save(user.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
