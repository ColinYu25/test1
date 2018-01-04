package com.safetys.zwfw.service.domain.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.safetys.zwfw.common.StringUtils;
import com.safetys.zwfw.core.SqlTemplate;
import com.safetys.zwfw.core.SqlTemplateFactory;
import com.safetys.zwfw.core.TableDefinition;
import com.safetys.zwfw.service.domain.SourceDBTemplate;

public class SourceDBTemplateImpl extends DBTemplateImpl implements
		SourceDBTemplate {

	public List<Map<String, Object>> findSyncList(TableDefinition definition) {
		SqlTemplate st = getQueryTemplate(definition);
		return jdbcTemplate.queryForList(st.getSql());
	}

	public Boolean updateSuccessMark(Map<String, Object> data,TableDefinition definition) {
		SqlTemplate st = getUpdateStatusTemplate(definition);
		data.put(SqlTemplateFactory.STATUS_PARAMETERNAME, definition.getStatusSuccess());
		if(StringUtils.isNotBlank(definition.getStatusTimeColumn())){
			data.put(SqlTemplateFactory.STATUS_TIME_PARAMETERNAME, new Date());
		}
		int result = update(st, data);
		return result>0;
	}

	public Boolean updateFailureMark(Map<String, Object> data,TableDefinition definition) {
		SqlTemplate st = getUpdateStatusTemplate(definition);
		data.put(SqlTemplateFactory.STATUS_PARAMETERNAME, definition.getStatusFailure());
		if(StringUtils.isNotBlank(definition.getStatusTimeColumn())){
			data.put(SqlTemplateFactory.STATUS_TIME_PARAMETERNAME, new Date());
		}
		int result = update(st, data);
		return result>0;
	}

}
