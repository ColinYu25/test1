package com.safetys.zwfw.service.domain.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.safetys.zwfw.core.TableDefinition;
import com.safetys.zwfw.core.TableDefinitionManager;
import com.safetys.zwfw.service.domain.DataSynchroService;
import com.safetys.zwfw.service.domain.SourceDBTemplate;
import com.safetys.zwfw.service.domain.TargetDBTemplate;
/**
 * 数据同步类
 * @author HelloFR
 *
 */
public class DataSynchroServiceImpl implements DataSynchroService{

	private static Logger logger = Logger.getLogger(DataSynchroServiceImpl.class);
	
	private List<TableDefinition> definitions = new ArrayList<TableDefinition>();
	//源数据
	private SourceDBTemplate sourceDB;
	//目标数据
	private TargetDBTemplate targetDB;
	
	private TableDefinitionManager definitionManager;
	
	public void execute(){
		try {
			long l = System.currentTimeMillis();
			if(CollectionUtils.isNotEmpty(definitions)){
				for (TableDefinition definition : definitions) {
					this.execute(definition);
				}
			}
			if( definitionManager != null && !definitionManager.isEmpty()){
				for (TableDefinition definition : definitionManager.getDefinitions()) {
					this.execute(definition);
				}
			}
			l = System.currentTimeMillis() - l;
			logger.info("本次同步总用时 "+(l/1000)+" 秒");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	public void execute(TableDefinition definition){
		logger.info("["+definition.getTableName()+"]准备同步...");
		BufferIterator iterator = new BufferIterator(sourceDB, definition);
		int sum = 0;
		int success_sum = 0;
		int success = 0;
		long l = System.currentTimeMillis();
		while(iterator.hasNext()){
			success = 0;
			List<Map<String, Object>> datas = iterator.next();
			logger.info("["+definition.getTableName()+"]缓冲加载"+datas.size()+"条同步数据，开始同步...");
			sum+=datas.size();
			Map<String, Object> data;
			for(int i = 0 ;i < datas.size();i++){
				data = datas.get(i);
				try {
					if(targetDB.save(data, definition)){
						sourceDB.updateSuccessMark(data, definition);
						success++;
					}else{
						sourceDB.updateFailureMark(data, definition);
					}
				} catch (Exception e) {
					e.printStackTrace();
					sourceDB.updateFailureMark(data, definition);
					logger.error("["+definition.getTableName()+"]同步异常",e);
				}
				logger.debug("["+definition.getTableName()+"] 缓存操作进度 "+i+"/"+datas.size());
			}
			logger.info("["+definition.getTableName()+"]本次缓冲数据操作结果："+success+"/"+datas.size());
			success_sum += success;
		}
		iterator.remove();
		l = System.currentTimeMillis()-l;
		logger.info("["+definition.getTableName()+"]同步数据结束(总数："+sum+"，成功："+success_sum+"，用时："+(l/1000)+"秒)");
	}

	public void setDefinitions(List<TableDefinition> definitions) {
		this.definitions = definitions;
	}

	public void setSourceDB(SourceDBTemplate sourceDB) {
		this.sourceDB = sourceDB;
	}

	public void setTargetDB(TargetDBTemplate targetDB) {
		this.targetDB = targetDB;
	}
	
	public TableDefinitionManager getDefinitionManager() {
		return definitionManager;
	}

	public void setDefinitionManager(TableDefinitionManager definitionManager) {
		this.definitionManager = definitionManager;
	}

	public class BufferIterator implements Iterator<List<Map<String, Object>>>{

		public BufferIterator(SourceDBTemplate sourceDB,TableDefinition definition){
			this.sourceDB = sourceDB;
			this.definition = definition;
		}
		
		private SourceDBTemplate sourceDB;
		
		private TableDefinition definition;
		
		private List<Map<String, Object>> datas;
		
		public boolean hasNext() {
			datas = sourceDB.findSyncList(definition);
			return CollectionUtils.isNotEmpty(datas);
		}

		public List<Map<String, Object>> next() {
			return this.datas;
		}

		public void remove() {
			datas = null;
			sourceDB = null;
			definition = null;
		}
	}
}
