package com.safetys.zwfw.service.domain;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.safetys.zwfw.core.SqlTemplate;
import com.safetys.zwfw.core.TableDefinition;
import com.safetys.zwfw.dialect.Dialect;

public interface DBTemplate {
	
	int update(SqlTemplate template, Map<String, Object> data);

	SqlTemplate getInsertTemplate(TableDefinition definition);

	SqlTemplate getUpdateTemplate(TableDefinition definition);

	SqlTemplate getUpdateStatusTemplate(TableDefinition definition);

	SqlTemplate getQueryTemplate(TableDefinition definition);

	SqlTemplate getExistsTemplate(TableDefinition definition);

	JdbcTemplate getJdbcTemplate();

	Dialect getDialect();
	
}
