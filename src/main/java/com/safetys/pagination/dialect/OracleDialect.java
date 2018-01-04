package com.safetys.pagination.dialect;

public class OracleDialect extends Dialect {

	protected static final String SQL_END_DELIMITER = ";";
	public static final String EMPTY = "";
	public static final int INDEX_NOT_FOUND = -1;

	public boolean supportsLimitOffset() {
		return true;
	}

	public String getCountString(String sql) {
		sql = trim(sql);
		String totalSql = sql.toUpperCase();
		totalSql = "SELECT COUNT(1) FROM " + substringAfter(totalSql, "FROM");
		totalSql = substringBefore(totalSql, "ORDER BY");
		totalSql = substringBefore(totalSql, "GROUP BY");

		return totalSql;
	}
	

	public String getLimitString(String sql, boolean hasOffset) {
		sql = trim(sql);
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (hasOffset) {
			pagingSelect
					.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset) {
			pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
		} else {
			pagingSelect.append(" ) where rownum <= ?");
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

	public String getLimitString(String sql, int offset, int limit) {
		sql = trim(sql);
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			pagingSelect
					.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
			pagingSelect.append(" ) row_ where rownum <= ")
					.append(offset + limit).append(" ) where rownum_ > ")
					.append(offset);
		} else {
			pagingSelect.append(" ) where rownum <= ").append(offset + limit);
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0,
					sql.length() - 1 - SQL_END_DELIMITER.length());
		}
		return sql;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String substringBefore(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}
}
