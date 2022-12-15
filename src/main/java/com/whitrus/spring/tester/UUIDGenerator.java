package com.whitrus.spring.tester;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class UUIDGenerator {
	public static void main(String[] args) throws IOException {
		var exporter = new UUIDGenerator();
		exporter.start();
	}

	private void start() throws IOException {		
		int total = 6000;
		int offset = 0;
		int batchSize = 1000;
		
		int count = 0;
		
		var sqlFile = new File("/home/noschang/Desktop/uuids.sql");

		try (var printer = new PrintWriter(sqlFile)) {

			var insert = "BEGIN\n\tINSERT INTO \"UUID\" (\"ID\",\"VALUE\")";
			var select = "\t\tSELECT %d,HEXTORAW('%s')";

			for (int i = 0; i < total; i++) {

				if (i % batchSize == 0) {
					printer.println(insert);
				}

				var uuid = UUID.randomUUID().toString().replace("-", "");
				var data = select.formatted(i + 1 + offset, uuid);

				printer.print(data);
				count++;

				if (i == total - 1 || count % batchSize == 0) {
					printer.println(" FROM DUAL;");
					printer.println("\tCOMMIT;");
					printer.println("END;");
//					printer.println("\\");
					printer.println();
					count = 0;
				} else {
					printer.println(" FROM DUAL UNION ALL");
				}

				printer.flush();
			}
		}
	}
}
