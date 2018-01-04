package com.safetys.zwfw.core;

import java.io.Serializable;

import com.safetys.zwfw.common.StringUtils;

/**
 * 表定义
 * @author HelloFR
 *
 */
public class TableDefinition implements Serializable{
	
	private static final long serialVersionUID = -8815407265761061942L;
	
	public static final String SPLIT_PATTERN = "[, ;\r\n]";
	public static final String DEFAULT_WAIT = "N";
	public static final String DEFAULT_SUCCESS = "Y";
	public static final String DEFAULT_FAILURE = "E";
	
	/**
	 * 别名
	 */
	private String aliasName;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 同步数据列集合
	 */
	private String[] columns;
	/**
	 * 状态标记列
	 */
	private String statusColumn;
	/**
	 * 表主键
	 */
	private String key;

	/**
	 * 联合主键
	 */
	private String[] compositeKey;
	/**
	 * 等待状态
	 */
	private String statusWait = DEFAULT_WAIT;
	/**
	 * 同步成功状态
	 */
	private String statusSuccess = DEFAULT_SUCCESS;
	/**
	 * 同步失败状态
	 */
	private String statusFailure = DEFAULT_FAILURE;
	/**
	 * 同步状态变更时间字段
	 */
	private String statusTimeColumn = "";
	
	/**
	 * 排序 (orderby="order by id desc")
	 */
	private String orderby;
	/**
	 * query 时缓存条数
	 */
	private Integer bufferSize = 50;
	

	
	public boolean isComposite(){
		return this.getCompositeKey()!=null&&this.getCompositeKey().length>0;
	}
	
	public void setColumns(String columns) {
		this.columns = StringUtils.split(columns,SPLIT_PATTERN);
	}
	public void setCompositeKey(String compositeKey) {
		this.compositeKey = StringUtils.split(compositeKey,SPLIT_PATTERN);
	}

	

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	

	public String getStatusColumn() {
		return statusColumn;
	}

	public void setStatusColumn(String statusColumn) {
		this.statusColumn = statusColumn;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String[] getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(String[] compositeKey) {
		this.compositeKey = compositeKey;
	}
	
	public String getStatusWait() {
		return statusWait;
	}

	public void setStatusWait(String statusWait) {
		this.statusWait = statusWait;
	}

	public String getStatusSuccess() {
		return statusSuccess;
	}

	public void setStatusSuccess(String statusSuccess) {
		this.statusSuccess = statusSuccess;
	}

	public String getStatusFailure() {
		return statusFailure;
	}

	public void setStatusFailure(String statusFailure) {
		this.statusFailure = statusFailure;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String getStatusTimeColumn() {
		return statusTimeColumn;
	}

	public void setStatusTimeColumn(String statusTimeColumn) {
		this.statusTimeColumn = statusTimeColumn;
	}
	
}