package com.safetys.zwfw.dialect;

public interface Dialect {

	/**
	 * 转换冲突列
	 * @param column
	 * @return
	 */
	public String convertColumnName(String column);
	/**
	 * 缓存SQL生成(分页)
	 * @param sql
	 * @param bufferSize
	 * @return
	 */
	public String getBufferSql(String sql,int bufferSize);
	
}
