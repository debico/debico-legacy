package br.com.debico.core.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TestDAOUtils {

    @Test
    public void testGenerateSQLInsertNamedParameters() {
	final String SQL = "INSERT INTO TABLE (COLUNA1,COLUNA2) VALUES (:COLUNA1,:COLUNA2)";
	final String sqlGen = DAOUtils.generateSQLInsertNamedParameters(
		"TABLE", "COLUNA1", "COLUNA2");

	assertThat(sqlGen, equalTo(SQL));
    }

}
