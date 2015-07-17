package br.com.debico.core.dao;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public final class DAOUtils {

    private static final String SQL_INSERT_SUFFIX = "INSERT INTO %s (";
    private static final String SQL_INSERT_VALUES = ") VALUES (";
    private static final String SQL_INSERT_POSFIX = ")";
    private static final char SEPARATOR = ',';
    private static final char PARAM_SUFFIX = ':';

    private DAOUtils() {

    }

    // TODO: fazer um algorítimo mais inteligente.
    /**
     * Gera um comando <code>INSERT</code> no format <i>Named Parameters</i>:
     * <code>INSERT INTO TABLE (COLUMN) VALUES (:VALUE)</code> <br/>
     * O nome do parâmetro será o mesmo da coluna.
     * 
     * @param tableName
     *            nome da tabela
     * @param fields
     *            array com os campos.
     * @return
     */
    public static String generateSQLInsertNamedParameters(
	    final String tableName, String... fields) {
	final StringBuffer sql = new StringBuffer(SQL_INSERT_SUFFIX.length());

	sql.append(String.format(SQL_INSERT_SUFFIX, tableName))
		.append(StringUtils.join(fields, SEPARATOR))
		.append(SQL_INSERT_VALUES)
		.append(StringUtils.join(Lists.transform(Arrays.asList(fields),
			new AddParamSuffixFunction()), SEPARATOR))
		.append(SQL_INSERT_POSFIX);

	return sql.toString();
    }

    private static final class AddParamSuffixFunction implements
	    Function<String, String> {
	@Override
	public String apply(String input) {
	    return PARAM_SUFFIX + input;
	}
    }

}
