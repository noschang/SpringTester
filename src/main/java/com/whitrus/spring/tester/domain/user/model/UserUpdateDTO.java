package com.whitrus.spring.tester.domain.user.model;

import static com.whitrus.spring.tester.domain.patch.PatchAction.SET;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.UpdateDTO;
import com.whitrus.spring.tester.domain.patch.PatchAction;
import com.whitrus.spring.tester.domain.patch.PatchModification;
import com.whitrus.spring.tester.domain.patch.ValidPatchModification;
import com.whitrus.spring.tester.domain.validation.FieldMatch;
import com.whitrus.spring.tester.domain.validation.conditional.ConditionalValidation;
import com.whitrus.spring.tester.util.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ConditionalValidation(ifPresent = "password", required = { "currentPassword", "passwordConfirmation" })
@FieldMatch(first = "password", second = "passwordConfirmation")

@Getter
@Setter
@NoArgsConstructor
@ToString
public final class UserUpdateDTO implements UpdateDTO<User> {

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
	public void applyUpdates(User user) {
		if (name != null) {
			applyNameUpdate(name.getAction(), name.getValue(), user);
		}

		if (login != null) {
			applyLoginUpdate(login.getAction(), login.getValue(), user);
		}

		if (password != null) {
			applyPasswordUpdate(password.getAction(), password.getValue(), user);
		}
	}

	private void applyNameUpdate(PatchAction action, String name, User user) {
		switch (action) {
		case SET -> {
			user.setName(name);
			user.setNormalizedName(StringUtils.normalize(name));
		}
		case UNSET -> notifyPossibleBug(action);
		}
	}

	private void applyLoginUpdate(PatchAction action, String login, User user) {
		switch (action) {
		case SET -> user.setLogin(login);
		case UNSET -> notifyPossibleBug(action);
		}
	}

	private void applyPasswordUpdate(PatchAction action, String password, User user) {

		/*
		 * The password is being encrypted at the entity level using a converter, so we
		 * don't need to encrypt it here.
		 * 
		 * Also, we don't need to check if the password confirmation matches because it
		 * is is being checked by the @FieldMatch annotation
		 */

		switch (action) {
		case SET -> user.setPassword(password);
		case UNSET -> notifyPossibleBug(action);
		}
	}

	private void notifyPossibleBug(PatchAction action) {
		throw new IllegalStateException(
				"Unexpected value: " + action + ". This should not happen and probably is a bug");
	}
}
