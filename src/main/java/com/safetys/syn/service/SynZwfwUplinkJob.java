package com.safetys.syn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.safetys.syn.BaseJob;
import com.safetys.syn.EventFire;
import com.safetys.zwfw.service.domain.DataSynchroService;

public class SynZwfwUplinkJob extends BaseJob {

	@Autowired
	@Qualifier("uplinkSynchroService")
	private DataSynchroService dataSynchroService;// 中心库
	
	public void doBiz() {
		try {
			logger.info("宁波市松香水烟花爆竹实名登记信息同步开始");
			EventFire.fire(close,"政务网上行操作（实名登记信息）",new SynZwfwEvent(dataSynchroService));
			logger.info("宁波市松香水烟花爆竹实名登记信息同步结束");
		} catch (Exception e) {
			logger.info(e);
		}
	}

}
