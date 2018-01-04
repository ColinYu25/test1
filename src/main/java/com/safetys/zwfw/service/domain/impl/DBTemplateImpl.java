package com.safetys.zwfw.service.domain.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.jdbc.core.JdbcTemplate;

import com.safetys.zwfw.core.SqlTemplate;
import com.safetys.zwfw.core.SqlTemplateFactory;
import com.safetys.zwfw.core.TableDefinition;
import com.safetys.zwfw.dialect.Dialect;
import com.safetys.zwfw.service.domain.DBTemplate;


public abstract class DBTemplateImpl implements DBTemplate{
	
	protected JdbcTemplate jdbcTemplate;
	
	protected Dialect dialect;
	
	private ConcurrentMap<TableDefinition, SqlTemplate> insertTemplateMap = new ConcurrentHashMap<TableDefinition, SqlTemplate>();
	private ConcurrentMap<TableDefinition, SqlTemplate> updateTemplateMap = new ConcurrentHashMap<TableDefinition, SqlTemplate>();
	private ConcurrentMap<TableDefinition, SqlTemplate> updateStatusTemplateMap = new ConcurrentHashMap<TableDefinition, SqlTemplate>();
	private ConcurrentMap<TableDefinition, SqlTemplate> queryTemplateMap = new ConcurrentHashMap<TableDefinition, SqlTemplate>();
	private ConcurrentMap<TableDefinition, SqlTemplate> existsTemplateMap = new ConcurrentHashMap<TableDefinition, SqlTemplate>();
	
	public int update(SqlTemplate template,Map<String, Object> data) {
		Object[] params = template.convertValues(data);
		return jdbcTemplate.update(template.getSql(), params);
	}
	
	public SqlTemplate getInsertTemplate(TableDefinition definition){
		SqlTemplate template = insertTemplateMap.get(definition);
		if(template == null){
			if(dialect!=null){
				template = SqlTemplateFactory.createInsertTemplate(definition,dialect);
			}else{
				template = SqlTemplateFactory.createInsertTemplate(definition);
			}
			insertTemplateMap.put(definition, template);
		}
		return template;
	}
	
	public SqlTemplate getUpdateTemplate(TableDefinition definition){
		SqlTemplate template = updateTemplateMap.get(definition);
		if(template == null){
			if(dialect!=null){
				template = SqlTemplateFactory.createUpdateTemplate(definition,dialect);
			}else{
				template = SqlTemplateFactory.createUpdateTemplate(definition);
			}
			updateTemplateMap.put(definition, template);
		}
		return template;
	}
	
	public SqlTemplate getUpdateStatusTemplate(TableDefinition definition){
		SqlTemplate template = updateStatusTemplateMap.get(definition);
		if(template == null){
			template = SqlTemplateFactory.createUpdateStatusTemplate(definition);
			updateStatusTemplateMap.put(definition, template);
		}
		return template;
	}
	
	public SqlTemplate getQueryTemplate(TableDefinition definition){
		SqlTemplate template = queryTemplateMap.get(definition);
		if(template == null){
			if(dialect!=null){
				template = SqlTemplateFactory.createQueryTemplate(definition,dialect);
			}else{
				template = SqlTemplateFactory.createQueryTemplate(definition);
			}
			queryTemplateMap.put(definition, template);
		}
		return template;
	}
	
	public SqlTemplate getExistsTemplate(TableDefinition definition){
		SqlTemplate template = existsTemplateMap.get(definition);
		if(template == null){
			
			template = SqlTemplateFactory.createExistsTemplate(definition);
			existsTemplateMap.put(definition, template);
		}
		return template;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	
	
}
