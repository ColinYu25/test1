package com.safetys.syn;

public interface IEvent {
	
	public static final String TOKEN = "CSZFHDXMTXSJKFD" ;
	
	public static final  int PAGESIZE = 100;
	
	public static final String SYS_COMPANY = "SYS_COMPANY";  //规上企业同步
	
	public static final String SYS_ALL_COMPANY = "SYS_ALL_COMPANY";  //所有企业入库同步
	
	public static final String SYS_CREDIT_COMPANY = "SYS_CREDIT_COMPANY";  //规上企业同步
	
	public static final String SYS_INC_GS_COMPANY = "SYS_INC_GS_COMPANY";  //工商前置机
	
	public static final String SYS_INC_ZJ_COMPANY = "SYS_INC_ZJ_COMPANY";  //质检前置机
	
	public static final String SYS_TZSB = "SYS_TZSB"; //特种设备
	
	void process();

}
