package com.whitrus.spring.tester.domain.user;

import java.util.function.Supplier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.whitrus.spring.tester.domain.user.exceptions.UserNotFoundException;
import com.whitrus.spring.tester.domain.user.model.User;
import com.whitrus.spring.tester.domain.user.model.UserDTO;
import com.whitrus.spring.tester.domain.user.model.UserInsertDTO;
import com.whitrus.spring.tester.domain.user.model.UserUpdateDTO;
import com.whitrus.spring.tester.domain.validation.EntityId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Validated
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllUsersAsDTO(@NotNull Pageable pageable) {
		return userRepository.findAllUsersAsDTO(pageable);
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllUsersWithDetailsAsDTO(@NotNull Pageable pageable) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Transactional(readOnly = true)
	public UserDTO findUserByIdAsDTO(@EntityId Long userId) {
		return userRepository.findUserByIdAsDTO(userId).orElseThrow(userNotFoundException(userId));
	}

	@Transactional(readOnly = true)
	public UserDTO findUserWithDetailsByIdAsDTO(@EntityId Long userId) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Transactional(readOnly = false)
	public Long insertNewUser(@Valid UserInsertDTO userInsertDTO) {

		User user = userInsertDTO.createNew();
		return userRepository.save(user).getId();
	}

	@Transactional(readOnly = false)
	public void updateUser(@NonNull Long userId, @NotNull @Valid UserUpdateDTO userUpdateDTO) {

		User user = userRepository.findById(userId).orElseThrow(userNotFoundException(userId));
		userUpdateDTO.applyUpdates(user);
	}

	private Supplier<UserNotFoundException> userNotFoundException(Long userId) {
		return () -> new UserNotFoundException(userId);
	}
}
