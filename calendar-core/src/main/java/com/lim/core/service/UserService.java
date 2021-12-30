package com.lim.core.service;

import com.lim.core.domain.entity.User;
import com.lim.core.domain.entity.repository.UserRepository;
import com.lim.core.dto.UserCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateReq userCreateReq) {
        userRepository.findByEmail(userCreateReq.getEmail())
            .ifPresent( check -> {
                throw new RuntimeException("user already existed");
            });
        return userRepository.save(new User(
                userCreateReq.getName(),
                userCreateReq.getEmail(),
                userCreateReq.getPassword(),
                userCreateReq.getBirthday()
        ));
    }

    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.getPassword().equals(password) ? user : null);
    }
}