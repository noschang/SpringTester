package com.whitrus.spring.tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswGenerator {
	public static void main(String[] args) throws IOException {
		File inFile = new File("/home/noschang/Desktop/passw.csv");
		File outFile = new File("/home/noschang/Desktop/passw.sql");

		String line = null;
		PasswordEncoder encoder = new BCryptPasswordEncoder(BCryptVersion.$2A, 12);

		try (BufferedReader reader = new BufferedReader(new FileReader(inFile));
				PrintWriter writer = new PrintWriter(outFile)) {
			while ((line = reader.readLine()) != null) {
				if (line.isBlank()) {
					continue;
				}
				
				var data = line.split(";");
				var id = data[0];
				var cpf = data[1];
				var passw = "!!bypass!!%s".formatted(cpf);

				var update = """
						UPDATE "USER" SET "PASSWORD" = '%s' WHERE "ID" = %s;\
						""";

				passw = encoder.encode(passw);
				update = update.formatted(passw, id);

				System.out.println(update);
				writer.println(update);
				writer.flush();
			}
		}
	}
}
