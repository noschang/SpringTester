package com.whitrus.spring.tester.config.hibernate;

import static java.sql.Types.DOUBLE;
import static java.sql.Types.VARCHAR;

import org.hibernate.dialect.Oracle10gDialect;
import org.springframework.stereotype.Component;

/**
 * Fixes some column data types that were wrong in the {@link Oracle10gDialect}.
 * Columns of type {@link String} with length greater than 2048 will be mapped
 * to 'clob', other strings will mapped to 'varchar2'. Columns of type
 * {@link Double} will be mapped to 'float'
 * 
 * @author Luiz Fernando Noschang
 */

@Component
public class Oracle11gDialect extends Oracle10gDialect {
	public Oracle11gDialect() {
		registerColumnType(VARCHAR, 2048, "varchar2($l char)");
		registerColumnType(VARCHAR, "clob");
		registerColumnType(DOUBLE, "float");
	}
}
