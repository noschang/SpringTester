package com.whitrus.spring.tester.domain.user.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.AttributeConverter;

public final class UserRoleConverter implements AttributeConverter<Set<UserRole>, String>
{
	public static final int ROLES_MAX_LENGTH = 64;

	private static final Pattern VERIFICATION_PATTERN;
	private static final Pattern VALUE_PATTERN;

	static
	{
		StringBuilder possibleValues = new StringBuilder();

		for (UserRole userRole : UserRole.values())
		{
			possibleValues.append(userRole.name());
			possibleValues.append("|");
		}

		if (possibleValues.length() > 0)
		{
			possibleValues.setLength(possibleValues.length() - 1);
		}

		String valuesList = possibleValues.toString();

		VERIFICATION_PATTERN = Pattern.compile("^(\\[(" + valuesList + ")\\])*$");

		VALUE_PATTERN = Pattern.compile("\\[(" + valuesList + ")\\]");
	}

	@Override
	public String convertToDatabaseColumn(Set<UserRole> userRoles)
	{
		if (userRoles == null || userRoles.isEmpty())
		{
			return null;
		}

		List<UserRole> sortedRoles = sortRoles(userRoles);
		StringBuilder builder = new StringBuilder();

		for (UserRole userRole : sortedRoles)
		{
			builder.append("[");
			builder.append(userRole.name());
			builder.append("]");
		}

		if (builder.length() > ROLES_MAX_LENGTH)
		{
			throw new RoleSetConversionException(String.format("The resulting string length is bigger than %d", ROLES_MAX_LENGTH));
		}

		return builder.toString();
	}

	@Override
	public Set<UserRole> convertToEntityAttribute(String databaseValue)
	{
		Set<UserRole> userRoles = new HashSet<>();

		if (databaseValue == null || databaseValue.isEmpty())
		{
			return userRoles;
		}

		Matcher verificationMatcher = VERIFICATION_PATTERN.matcher(databaseValue);

		if (verificationMatcher.find())
		{
			Matcher valueMatcher = VALUE_PATTERN.matcher(databaseValue);

			while (valueMatcher.find())
			{
				String name = valueMatcher.group(1);

				userRoles.add(UserRole.valueOf(name));
			}

			return userRoles;
		}

		throw new RuntimeException(String.format("The database value is invalid. It must follow the pattern: %s", VERIFICATION_PATTERN.pattern()));
	}

	private List<UserRole> sortRoles(Set<UserRole> userRoles)
	{
		List<UserRole> sortedRoles = new ArrayList<>(userRoles.size());

		for (UserRole userRole : userRoles)
		{
			sortedRoles.add(userRole);
		}

		Collections.sort(sortedRoles, new RoleComparator());

		return sortedRoles;
	}

	private static class RoleComparator implements Comparator<UserRole>
	{
		@Override
		public int compare(UserRole o1, UserRole o2)
		{
			return o1.ordinal() - o2.ordinal();
		}
	}

	public static final class RoleSetConversionException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public RoleSetConversionException(String message)
		{
			super(message);
		}
	}
}
