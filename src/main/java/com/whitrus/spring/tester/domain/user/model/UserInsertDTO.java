package com.whitrus.spring.tester.domain.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.PersistenEntityInsertDTO;
import com.whitrus.spring.tester.domain.validation.FieldMatch;
import com.whitrus.spring.tester.domain.validation.conditional.ConditionalValidation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ConditionalValidation(ifPresent = "password", required = "passwordConfirmation")
@FieldMatch(first = "password", second = "passwordConfirmation")

@Getter
@Setter
@NoArgsConstructor
@ToString
public final class UserInsertDTO implements PersistenEntityInsertDTO<User, Void> {

	@NotBlank
	@Size(min = 3, max = 64)
	private String name;

	@NotBlank
	@Size(min = 3, max = 64)
	private String login;

	@NotBlank
	@Size(min = 3, max = 32)
	private String password;

	@NotBlank
	@Size(min = 3, max = 32)
	private String passwordConfirmation;

	private UserDTODependencies dependencies;

	@Override
	public User createNew() {
		User user = new User();

		user.setActive(true);
		user.setName(name);
		user.setLogin(login);

		/*
		 * The password is being encrypted at the entity level using a converter, so we
		 * don't need to encrypt it here.
		 * 
		 * Also, we don't need to check if the password confirmation matches because it
		 * is is being checked by the @FieldMatch annotation
		 */
		user.setPassword(password);

		return user;
	}
}
