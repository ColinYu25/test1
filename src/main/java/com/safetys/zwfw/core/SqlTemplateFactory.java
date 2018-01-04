package com.safetys.zwfw.core;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.safetys.zwfw.common.MessageFormat;
import com.safetys.zwfw.common.StringUtils;
import com.safetys.zwfw.common.Utils;
import com.safetys.zwfw.dialect.Dialect;

public class SqlTemplateFactory {
	
	private static Logger logger = Logger.getLogger(SqlTemplateFactory.class);
	/**
	 * 状态参数名称
	 */
	public static final String STATUS_PARAMETERNAME = "STATUS";
	public static final String STATUS_TIME_PARAMETERNAME = "STATUS_TIME";
	
	private static final String QUERY_TEMPLATE = "SELECT {0} FROM {1} WHERE {2}";
	private static final String EXISTS_TEMPLATE = "SELECT COUNT(1) FROM {0} WHERE {1}";
	private static final String INSERT_TEMPLATE = "INSERT INTO {0}({1}) VALUES ({2})";
	private static final String UPDATE_STATUS_TEMPLATE = "UPDATE {0} SET {1}=? WHERE {2}";
	private static final String UPDATE_STATUS_TEMPLATE2 = "UPDATE {0} SET {1}=?,{2} WHERE {3}";
	private static final String UPDATE_TEMPLATE = "UPDATE {0} SET {1} WHERE {2}";

	/**
	 * 创建查询语句
	 * @param definition
	 * @return
	 */
	public static SqlTemplate createQueryTemplate(TableDefinition definition){
		String select = getSelectColumns(definition);
		String table = getFromTable(definition);
		String where = definition.getStatusColumn()+"='"+definition.getStatusWait()+"'";
		if(StringUtils.isNotBlank(definition.getOrderby())){
			where += " order by "+definition.getOrderby();
		}
		String sql = MessageFormat.format(QUERY_TEMPLATE, select,table,where);
		logger.info("创建query - "+definition.getTableName()+" ("+sql+")");
		return SqlTemplate.newInstance(sql, new ArrayList<String>());
	}
	
	public static SqlTemplate createQueryTemplate(TableDefinition definition,Dialect dialect){
		String select = getSelectColumns(definition,dialect);
		String table = getFromTable(definition);
		String where = definition.getStatusColumn()+"='"+definition.getStatusWait()+"'";
		if(StringUtils.isNotBlank(definition.getOrderby())){
			where += " order by "+definition.getOrderby();
		}
		String sql = MessageFormat.format(QUERY_TEMPLATE, select,table,where);
		sql = dialect.getBufferSql(sql,definition.getBufferSize());
		logger.info("创建query - "+definition.getTableName()+" ("+sql+")");
		return SqlTemplate.newInstance(sql, new ArrayList<String>());
	}
	
	public static SqlTemplate createExistsTemplate(TableDefinition definition){
		String where = getWherePrimarykeys(definition);
		String sql = MessageFormat.format(EXISTS_TEMPLATE, definition.getTableName(),where);
		List<String> parameterNames = new ArrayList<String>();
		if (definition.isComposite()) {
			CollectionUtils.addAll(parameterNames, definition.getCompositeKey());
		} else {
			parameterNames.add(definition.getKey());
		}
		logger.info("创建exists - "+definition.getTableName()+" ("+sql+")");
		return SqlTemplate.newInstance(sql, parameterNames);
	}
	
	/**
	 * 创建插入语句
	 * @param definition
	 * @return
	 */
	public static SqlTemplate createInsertTemplate(TableDefinition definition){
		String v = Utils.StringFor(definition.getColumns().length);
		String sql = MessageFormat.format(INSERT_TEMPLATE, definition.getTableName(),StringUtils.join(definition.getColumns(),","),v);
		List<String> parameterNames = new ArrayList<String>();
		CollectionUtils.addAll(parameterNames, definition.getColumns());
		logger.info("创建insert - "+definition.getTableName()+" ("+sql+")");
		return SqlTemplate.newInstance(sql, parameterNames);
	}
	
	public static SqlTemplate createInsertTemplate(TableDefinition definition,Dialect dialect){
		String v = Utils.StringFor(definition.getColumns().length);
		String sql = MessageFormat.format(INSERT_TEMPLATE, definition.getTableName(),
				Utils.getSelectColumns(definition.getColumns(), dialect),v);
		List<String> parameterNames = new ArrayList<String>();
		CollectionUtils.addAll(parameterNames, definition.getColumns());
		logger.info("创建insert - "+definition.getTableName()+" ("+sql+")");
		return SqlTemplate.newInstance(sql, parameterNames);
	}
	
	/**
	 * 创建更新语句（更新状态）
	 * @param definition
	 * @return
	 */
	public static SqlTemplate createUpdateStatusTemplate(TableDefinition definition) {
		String where = getWherePrimarykeys(definition);
		List<String> parameterNames = new ArrayList<String>();
		parameterNames.add(STATUS_PARAMETERNAME);
		String sql = MessageFormat.format(UPDATE_STATUS_TEMPLATE,
				definition.getTableName(), definition.getStatusColumn(), where);
		if(StringUtils.isNotBlank(definition.getStatusTimeColumn())){
			String s = definition.getStatusTimeColumn()+" = ? ";
			sql = MessageFormat.format(UPDATE_STATUS_TEMPLATE2, definition.getTableName(), definition.getStatusColumn(),s, where);
			parameterNames.add(STATUS_TIME_PARAMETERNAME);
		}
		//statusTimeColumn
		if (definition.isComposite()) {
			CollectionUtils.addAll(parameterNames, definition.getCompositeKey());
		} else {
			parameterNames.add(definition.getKey());
		}
		logger.info("创建updateStatus - "+definition.getTableName()+" ("+sql+")");
		return SqlTemplate.newInstance(sql, parameterNames);
	}
	/**
	 * 创建更新语句
	 * @param definition
	 * @return
	 */
	public static SqlTemplate createUpdateTemplate(TableDefinition definition,Dialect dialect){
		String where = getWherePrimarykeys(definition);
		List<String> keys = new ArrayList<String>();
		if (definition.isComposite()) {
			CollectionUtils.addAll(keys, definition.getCompositeKey());
		} else {
			keys.add(definition.getKey());
		}
		String set = Utils.getSetValue(definition.getColumns(), keys,dialect);
		List<String> parameterNames = Utils.exclude(definition.getColumns(), keys);
		parameterNames.addAll(keys);
		String sql = MessageFormat.format(UPDATE_TEMPLATE, definition.getTableName(),set,where);
		logger.info("创建update - "+definition.getTableName()+" ("+sql+")");
		return SqlTemplate.newInstance(sql, parameterNames);
	}
	
	public static SqlTemplate createUpdateTemplate(TableDefinition definition){
		String where = getWherePrimarykeys(definition);
		List<String> keys = new ArrayList<String>();
		if (definition.isComposite()) {
			CollectionUtils.addAll(keys, definition.getCompositeKey());
		} else {
			keys.add(definition.getKey());
		}
		String set = Utils.getSetValue(definition.getColumns(), keys);
		List<String> parameterNames = Utils.exclude(definition.getColumns(), keys);
		parameterNames.addAll(keys);
		String sql = MessageFormat.format(UPDATE_TEMPLATE, definition.getTableName(),set,where);
		logger.info("创建update - "+definition.getTableName()+" ("+sql+")");
		return SqlTemplate.newInstance(sql, parameterNames);
	}
	
	private static String getSelectColumns(TableDefinition definition){
		if(StringUtils.isBlank(definition.getAliasName())){
			return StringUtils.join(definition.getColumns(), ",");
		}else{
			return definition.getAliasName()+"."+StringUtils.join(definition.getColumns(),","+definition.getAliasName()+".");
		}
	}
	
	private static String getSelectColumns(TableDefinition definition,Dialect dialect){
		List<String> cols = new ArrayList<String>();
		boolean alias = StringUtils.isNotBlank(definition.getAliasName());
		for(String colunm : definition.getColumns()){
			if(alias){
				cols.add(definition.getAliasName()+"."+dialect.convertColumnName(colunm));
			}else{
				cols.add(dialect.convertColumnName(colunm));
			}
		}
		return StringUtils.join(cols,",");
	}
	
	private static String getFromTable(TableDefinition definition){
		return definition.getTableName()+(StringUtils.isNotBlank(definition.getAliasName())?" "+definition.getAliasName():"");
	}
	
	private static String getWherePrimarykeys(TableDefinition definition){
		List<String> primaryKeys= new ArrayList<String>();
		if(definition.isComposite()){
			CollectionUtils.addAll(primaryKeys, definition.getCompositeKey());
		}else{
			primaryKeys.add(definition.getKey());
		}
		StringBuffer str = new StringBuffer();
		for(int i=0;i<primaryKeys.size();i++){
			if(i>0){
				str.append(" and ");
			}
			str.append(primaryKeys.get(i)).append("=?");
		}
		return str.toString();
	}
}
