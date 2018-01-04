package com.safetys.syn.service;

import com.safetys.syn.BaseEvent;
import com.safetys.zwfw.service.domain.DataSynchroService;

public class SynZwfwEvent extends BaseEvent {

	public SynZwfwEvent(DataSynchroService dataSynchroService){
		this.dataSynchroService = dataSynchroService;
	}
	
	private DataSynchroService dataSynchroService;
	
	public void process() {
		dataSynchroService.execute();
	}

}
