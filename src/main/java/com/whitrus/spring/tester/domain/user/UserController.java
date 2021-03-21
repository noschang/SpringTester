package com.whitrus.spring.tester.domain.user;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.whitrus.spring.tester.domain.user.model.UserDTO;
import com.whitrus.spring.tester.domain.user.model.UserInsertDTO;
import com.whitrus.spring.tester.domain.user.model.UserUpdateDTO;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	@ResponseStatus(OK)
	public Page<UserDTO> findAllUsers(@RequestParam(defaultValue = "basic") String view,
			@SortDefault(value = "name", direction = ASC) Pageable pageable) {

		if (view.equals("details")) {
			return userService.findAllUsersWithDetailsAsDTO(pageable);
		}

		return userService.findAllUsersAsDTO(pageable);
	}

	@GetMapping("/{userId}")
	@ResponseStatus(OK)
	public UserDTO findUserById(@PathVariable Long userId, @RequestParam(defaultValue = "basic") String view) {

		if (view.equals("details")) {
			return userService.findUserWithDetailsByIdAsDTO(userId);
		}

		return userService.findUserByIdAsDTO(userId);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public ResponseEntity<UserDTO> insertNewUser(@Valid @RequestBody UserInsertDTO userInsertDTO,
			@RequestParam(defaultValue = "basic") String view) {

		Long userId = userService.insertNewUser(userInsertDTO);
		return createUserResponse(userId, view);
	}

	@PatchMapping("/{userId}")
	@ResponseStatus(OK)
	public UserDTO updateUser(@PathVariable Long userId, @RequestBody @Valid UserUpdateDTO userUpdateDTO,
			@RequestParam(defaultValue = "basic") String view) {

		userService.updateUser(userId, userUpdateDTO);
		return findUserById(userId, view);
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(OK)
	public UserDTO deleteUser(@PathVariable Long userId, @RequestParam(defaultValue = "basic") String view) {
		UserDTO userDTO = findUserById(userId, view);
		userService.deleteUser(userId);
		
		return userDTO;
	}

	private ResponseEntity<UserDTO> createUserResponse(Long userId, String view) {
		UserDTO post = findUserById(userId, view);
		URI uri = linkTo(methodOn(UserController.class).findUserById(userId, null)).toUri();

		return ResponseEntity.created(uri).body(post);
	}
}
