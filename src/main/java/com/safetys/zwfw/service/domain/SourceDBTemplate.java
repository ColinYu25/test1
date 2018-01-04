package com.safetys.zwfw.service.domain;

import java.util.List;
import java.util.Map;

import com.safetys.zwfw.core.TableDefinition;

public interface SourceDBTemplate {
	
	public List<Map<String, Object>> findSyncList(TableDefinition definition);

	/**
	 * 标记成功
	 * 
	 * @return
	 */
	public Boolean updateSuccessMark(Map<String, Object> data,TableDefinition definition);

	/**
	 * 标记失败
	 * 
	 * @return
	 */
	public Boolean updateFailureMark(Map<String, Object> data,TableDefinition definition);

}
