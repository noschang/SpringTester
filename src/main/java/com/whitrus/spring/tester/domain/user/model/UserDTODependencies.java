package com.whitrus.spring.tester.domain.user.model;

import com.whitrus.spring.tester.domain.user.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class UserDTODependencies {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
}
