package com.whitrus.spring.tester;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Testinho {
	public static void main(String[] args) {
		
		PasswordEncoder encoder = new BCryptPasswordEncoder(5);
		
		String rawPassword = "I'm a naughty boy!";
		String encodedPassword;
		
		long init = System.currentTimeMillis();
		encodedPassword = encoder.encode(rawPassword);
		long end = System.currentTimeMillis();
		
		System.out.println("Encrypted password: " +  encodedPassword);
		System.out.println("Time taken: " + (end - init));
		System.out.println("They match? " + encoder.matches("I'm a naughty boy!", encodedPassword));

		System.out.println(encoder.encode(encodedPassword));
	}
}