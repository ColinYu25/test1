package com.safetys.zwfw.dialect;

public class OracleDialect implements Dialect {

	public String convertColumnName(String colunm) {
		return colunm;
	}

	public String getBufferSql(String sql, int bufferSize) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append("select * from (");
		bufferSql.append(sql);
		bufferSql.append(") where rownum <= ").append(bufferSize);
		return bufferSql.toString();
	}

}
