package com.safetys.zwfw.dialect;

public class MySqlDialect implements Dialect {

	public String convertColumnName(String colunm) {
		return "`"+colunm+"`";
	}

	public String getBufferSql(String sql, int bufferSize) {
		return sql + " LIMIT "+bufferSize;
	}

}
