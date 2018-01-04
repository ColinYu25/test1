package com.safetys.syn;
import org.apache.log4j.Logger;

public abstract class BaseJob {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public  boolean close = true;
	
	
	public abstract void doBiz();
	
	public boolean isClose() {
		return close;
	}


	public void setClose(boolean close) {
		this.close = close;
	}

}
