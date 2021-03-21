package com.whitrus.spring.tester.domain.user.model;

import static com.whitrus.spring.tester.domain.patch.PatchAction.SET;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.PersistentEntityModificationException;
import com.whitrus.spring.tester.domain.PersistentEntityUpdateDTO;
import com.whitrus.spring.tester.domain.patch.PatchAction;
import com.whitrus.spring.tester.domain.patch.PatchModification;
import com.whitrus.spring.tester.domain.patch.ValidPatchModification;
import com.whitrus.spring.tester.domain.validation.FieldMatch;
import com.whitrus.spring.tester.domain.validation.conditional.ConditionalValidation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ConditionalValidation(ifPresent = "password", required = { "passwordConfirmation", "currentPassword" })
@FieldMatch(first = "password", second = "passwordConfirmation")

@Getter
@Setter
@NoArgsConstructor
@ToString
public final class UserUpdateDTO implements PersistentEntityUpdateDTO<User, UserDTODependencies> {

	private UserDTODependencies dependencies;

	@ValidPatchModification(SET)
	private PatchModification<@NotBlank @Size(min = 3, max = 64) String> name;

	@ValidPatchModification(SET)
	private PatchModification<@NotBlank @Size(min = 3, max = 64) String> login;

	@ValidPatchModification(SET)
	private PatchModification<@NotBlank @Size(min = 3, max = 32) String> currentPassword;

	@ValidPatchModification(SET)
	private PatchModification<@NotBlank @Size(min = 3, max = 32) String> password;

	@ValidPatchModification(SET)
	private PatchModification<@NotBlank @Size(min = 3, max = 32) String> passwordConfirmation;

	@Override
	public void setDependencies(UserDTODependencies dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public void applyUpdates(User user) {
		if (name != null) {
			applyNameUpdate(user, name.getAction(), name.getValue());
		}

		if (login != null) {
			applyLoginUpdate(user, login.getAction(), login.getValue());
		}

		if (password != null) {
			applyPasswordUpdate(user, password.getAction(), password.getValue(), currentPassword.getValue());
		}
	}

	private void applyNameUpdate(User user, PatchAction action, String name) {
		switch (action) {
		case SET -> user.setName(name);
		case UNSET -> notifyPossibleBug(action);
		}
	}

	private void applyLoginUpdate(User user, PatchAction action, String login) {
		switch (action) {
		case SET -> user.setLogin(login);
		case UNSET -> notifyPossibleBug(action);
		}
	}

	private void applyPasswordUpdate(User user, PatchAction action, String password, String currentPassword) {
		/*
		 * The password is being encrypted at the entity level using a converter, so we
		 * don't need to encrypt it here.
		 * 
		 * Also, we don't need to check if the password confirmation matches because it
		 * is is being checked by the @FieldMatch annotation
		 */

		switch (action) {
		case SET -> {

			String userPassword = dependencies.getUserRepository().findUserPasswordById(user.getId());
			boolean passwordsMatch = dependencies.getPasswordEncoder().matches(currentPassword, userPassword);

			if (passwordsMatch) {
				user.setPassword(password);
			} else {
				throw new PersistentEntityModificationException(
						"the informed current password don't match the user password");
			}
		}
		case UNSET -> notifyPossibleBug(action);
		}
	}

	private void notifyPossibleBug(PatchAction action) {
		throw new IllegalStateException(
				"Unexpected value: " + action + ". This should not happen and probably is a bug");
	}
}
