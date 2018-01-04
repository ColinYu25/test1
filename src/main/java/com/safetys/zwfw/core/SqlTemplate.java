package com.safetys.zwfw.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class SqlTemplate {

	public static SqlTemplate newInstance(String sql,List<String> parameterNames){
		return new SqlTemplate(sql, parameterNames);
	}
	
	
	public SqlTemplate(String sql,List<String> parameterNames){
		this.sql = sql;
		this.parameterNames = parameterNames;
	}
	
	private String sql;
	
	private List<String> parameterNames;
	
	
	public String getSql(){
		return this.sql;
	}
	
	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public Object[] convertValues(Map<String, Object> parameter){
		List<Object> result = new ArrayList<Object>();
		for(String name : parameterNames){
			result.add(parameter.get(name));
		}
		return result.toArray();
	}

	public String toString() {
		return getSql()+" ["+StringUtils.join(parameterNames, ",")+"]";
	}
	
	
	
}
