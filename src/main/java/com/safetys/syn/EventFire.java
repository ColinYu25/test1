package com.safetys.syn;
import org.apache.log4j.Logger;

/**
 * 同步事件执行类
 * @author zhaozhi3758
 */
public class EventFire {
	protected static Logger logger = Logger.getLogger(EventFire.class);
	
	public static void fire(IEvent event) throws Exception{
		event.process();
	}
	
	public static void fire(boolean close,String msg,IEvent event) throws Exception{
		if(close){
			logger.info(msg+"处于关闭状态");
			return;
		}
		event.process();
	}

}
