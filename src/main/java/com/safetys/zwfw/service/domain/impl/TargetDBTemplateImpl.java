package com.safetys.zwfw.service.domain.impl;

import java.util.Map;

import com.safetys.zwfw.core.SqlTemplate;
import com.safetys.zwfw.core.TableDefinition;
import com.safetys.zwfw.service.domain.TargetDBTemplate;

public class TargetDBTemplateImpl extends DBTemplateImpl implements
		TargetDBTemplate {

	public Boolean save(Map<String, Object> data, TableDefinition definition) {
		SqlTemplate st = getInsertTemplate(definition);
//		if(exist(data, definition)){
//			st = getUpdateTemplate(definition);
//		}
		int result = update(st, data);
		return result>0;
	}
	
	public Boolean exist(Map<String, Object> data, TableDefinition definition){
		SqlTemplate st = getExistsTemplate(definition);
		Object[] params = st.convertValues(data);
		int result = jdbcTemplate.queryForObject(st.getSql(),params,Integer.class);
		return result>0;
	}

}
