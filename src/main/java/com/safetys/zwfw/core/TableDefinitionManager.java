package com.safetys.zwfw.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ClassUtils;
/**
 * 表定义管理类
 * @author HelloFR
 *
 */
public class TableDefinitionManager implements BeanPostProcessor{

	private static Logger logger = Logger.getLogger(TableDefinitionManager.class);
	
	private Class<?> interfaceClass;
	
	private List<TableDefinition> definitions = new ArrayList<TableDefinition>();
	
	
	public Boolean isEmpty(){
		return definitions.isEmpty();
	}
	
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		//获取表定义对象
		if((bean instanceof TableDefinition) 
				&& ClassUtils.isAssignableValue(interfaceClass, bean)){
			definitions.add((TableDefinition)bean);
			logger.info("beanName : "+beanName+" instanceof "+interfaceClass.getName());
		}
		return bean;
	}
	
	public List<TableDefinition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<TableDefinition> definitions) {
		this.definitions = definitions;
	}

	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	
}