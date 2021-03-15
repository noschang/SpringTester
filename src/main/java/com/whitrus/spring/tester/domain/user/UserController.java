package com.whitrus.spring.tester.domain.user;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import javax.validation.Valid;

import com.whitrus.spring.tester.domain.user.model.UserDTO;
import com.whitrus.spring.tester.domain.user.model.UserInsertDTO;
import com.whitrus.spring.tester.domain.user.model.UserUpdateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAllUsers(@RequestParam(defaultValue = "basic") String view,
			@SortDefault(value = "name", direction = ASC) Pageable pageable) {

		if (view.equals("details")) {
			return ResponseEntity.ok(userService.findAllUsersWithDetailsAsDTO(pageable));
		}

		return ResponseEntity.ok(userService.findAllUsersAsDTO(pageable));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> findUserById(@PathVariable Long userId,
			@RequestParam(defaultValue = "basic") String view) {

		if (view.equals("details")) {
			return ResponseEntity.ok(userService.findUserWithDetailsByIdAsDTO(userId));
		}

		return ResponseEntity.ok(userService.findUserByIdAsDTO(userId));
	}

	@PostMapping
	public ResponseEntity<UserDTO> insertNewUser(@Valid @RequestBody UserInsertDTO userInsertDTO,
			@RequestParam(defaultValue = "basic") String view) {

		return createUserResponse(userService.insertNewUser(userInsertDTO), view);
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId,
			@RequestBody @Valid UserUpdateDTO userUpdateDTO, @RequestParam(defaultValue = "basic") String view) {

		userService.updateUser(userId, userUpdateDTO);
		return findUserById(userId, view);
	}

	private ResponseEntity<UserDTO> createUserResponse(Long userId, String view) {
		UserDTO post = findUserById(userId, view).getBody();
		URI uri = linkTo(methodOn(UserController.class).findUserById(userId, null)).toUri();

		return ResponseEntity.created(uri).body(post);
	}
}
