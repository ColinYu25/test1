package com.safetys.zwfw.service.domain;

import java.util.Map;

import com.safetys.zwfw.core.TableDefinition;

public interface TargetDBTemplate {
	
	public Boolean save(Map<String, Object> data,TableDefinition definition);
	
	public Boolean exist(Map<String, Object> data, TableDefinition definition);
	
}
