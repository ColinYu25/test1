package com.safetys.zwfw;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.safetys.zwfw.service.domain.DataSynchroService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:spring/spring-db.xml",
		"classpath:spring/spring-zwfw-db.xml",
		"classpath:spring/spring-zwfw-table.xml" })
public class ZwfwTest {

	@Resource(name = "downlinkSynchroService")
	private DataSynchroService downlinkSynchroService;
	
	@Resource(name = "uplinkSynchroService")
	private DataSynchroService uplinkSynchroService;

	/**
	 * 下行数据同步
	 */
	@Test
	public void downlinkSynchro() {
		downlinkSynchroService.execute();
	}
	/**
	 * 上行数据同步
	 */
	@Test
	public void uplinkSynchro(){
		uplinkSynchroService.execute();
	}
	
}