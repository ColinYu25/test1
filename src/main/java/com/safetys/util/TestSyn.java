package com.safetys.util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import cn.safetys.ansheng.service.bean.DataEx_COMPANY_BASIC_ServiceBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.safetys.syn.BaseEvent;
import cn.safetys.ansheng.service.DataEx_COMPANY_BASIC_Service;
import cn.safetys.tuxun.response.ResponseDataE;

/**
 * 同步测试类
 * @author zhaozhi3758
 */
public class TestSyn extends BaseEvent{
	
	private static DataEx_COMPANY_BASIC_Service dataEx_COMPANY_BASIC_Service;
	
	static {
		BeanFactory factory = new ClassPathXmlApplicationContext(new String[] {"dataEx-spring-hibernate.xml","dataEx-applicationContext-beans.xml" });
		dataEx_COMPANY_BASIC_Service = (DataEx_COMPANY_BASIC_ServiceBean) factory.getBean("dataEx_COMPANY_BASIC_Service");
	}
	
	
	public static  String getGsSql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select c.COMPANY_NAME,c.ORG_CODE,c.SECOND_AREA,c.ESTABLISHMENT_DAY,c.LEGAL_PERSON,c.FAX,c.PRINCIPAL_PERSON,");
		sql.append("c.PRINCIPAL_TELEPHONE,c.PRINCIPAL_MOBILE,p.SECURITY_PRINCIPAL_PERSON,p.SECURITY_PRINCIPAL_TELEPHONE,p.SECURITY_PRINCIPAL_MOBILE,");
		sql.append("c.REG_ADDRESS,c.BUSINESS_ADDRESS,c.ZIP_CODE,c.ECONOMIC_TYPE2,c.MANAGEMENT_LEVEL1,c.BUSINESS_SCOPE,c.REG_CAPITAL,");
		sql.append("c.YEAR_SALES,c.FLOOR_AREA,c.EMPLOYEE_ACTUAL_NUM,c.LONGITUDE,c.DIMENSIONALITY,c.UUID,c.ID,c.CREDIT_GRADE");
		sql.append(" from t_company c ");
		sql.append(" left join T_COMPANY_SAFETY_PRODUCTION p on p.company_id = c.id ");
		sql.append(" where  c.org_code  in ( ");
		sql.append("   select org_code from ls_t_company");
		sql.append(" ) ");
		sql.append(" and c.IS_ENTERPRISE =1 and c.credit_status =0 and c.is_deleted =0 and c.id=188025");
		return sql.toString();
		
	}
	
	
	public static void main(String[] args) throws Exception {
		List< Map<String, Object> > list = JdbcUtil.read(getGsSql());
		synGs(list);
    }
	
	
	//规上企业
	public static void synGs(List< Map<String, Object> > list) throws Exception{
		
		if(list != null ) { 
			for(Map<String, Object> map : list ){
				String COMPANY_NAME = map.get("COMPANY_NAME").toString();
				String UUID = map.get("UUID").toString();
				String ID =  map.get("ID").toString();
				Properties properties = new Properties();
				properties.put("REGISTER_CODE", ""); // 注册号     --->  默认
				
				String ORG_CODE = getObjValue(map.get("ORG_CODE"));
				if(ORG_CODE.indexOf("-") >-1){
					ORG_CODE = ORG_CODE.substring(0, ORG_CODE.indexOf("-"));
				}
				
				properties.put("ORG_CODE", ORG_CODE ); //  组织机构代码      --->  ORG_CODE
				
				String SECOND_AREA =  getObjValue(map.get("SECOND_AREA"));
				SECOND_AREA = SECOND_AREA.equals("") ? "330200000000" : SECOND_AREA;
				
				//省局6位
				if(SECOND_AREA.length() > 6){
					SECOND_AREA = SECOND_AREA.substring(0, 6);
				}
				
				properties.put("DISTRICT_CODE", SECOND_AREA );//【必填】 县级行政区域代码  ---> SECOND_AREA 对应 二级区域，如果为空  放置宁波市区域
				
				Object obj = map.get("ESTABLISHMENT_DAY");
				String FOUND_DATE = "";
				if(obj != null){
					FOUND_DATE = DateUtil.dateFormat((Date)obj,"yyyyMMdd");
				}
				
				properties.put("FOUND_DATE", FOUND_DATE);//成立日期 （8位日期）--- ESTABLISHMENT_DAY 
				properties.put("LEGAL_PERSON", getObjValue(map.get("LEGAL_PERSON")));// 法定代表 ---  LEGAL_PERSON
				
				String PRINCIPAL_PHONE = ""; //必填项默认值
				String FAX = getObjValue(map.get("FAX"));
				if(!FAX.equals("")){
					PRINCIPAL_PHONE = FAX;
				}
				
				properties.put("PRINCIPAL_PHONE", PRINCIPAL_PHONE);// 联系电话  ---  ======= FAX
				properties.put("PRINCIPAL_EMAIL", "");//   --->  默认
				properties.put("CO_FOX", FAX);//单位传真 （100个字符）   --- ======== FAX
				
				String psql = "select p.SECURITY_PRINCIPAL_PERSON,p.SECURITY_PRINCIPAL_TELEPHONE,p.SECURITY_PRINCIPAL_MOBILE from T_COMPANY_SAFETY_PRODUCTION p where p.company_id ="+ID;
				properties.put("PERSON_IN_CHARGE", getObjValue(map.get("PRINCIPAL_PERSON")));// 主要负责人（50个字符）   --- PRINCIPAL_PERSON
				properties.put("PIC_TEL",  getObjValue(map.get("PRINCIPAL_TELEPHONE")));// 主要负责人固定电话（100） --- PRINCIPAL_TELEPHONE
				properties.put("PIC_MOBILE",  getObjValue(map.get("PRINCIPAL_MOBILE")));// 主要负责人手机（100） --- PRINCIPAL_MOBILE
				properties.put("PIC_EMAIL", "");// 主要负责人邮箱
				
//				String HEAD_OF_SECURITY= "",HOS_TEL="",HOS_MOBILE=""; //不允许 “ / ” 默认值
				String SECURITY_PRINCIPAL_PERSON = getObjValue(map.get("SECURITY_PRINCIPAL_PERSON"));
				String SECURITY_PRINCIPAL_TELEPHONE = getObjValue(map.get("SECURITY_PRINCIPAL_TELEPHONE"));
				String SECURITY_PRINCIPAL_MOBILE = getObjValue(map.get("SECURITY_PRINCIPAL_MOBILE"));
				
//				if(!SECURITY_PRINCIPAL_PERSON.equals("")){
//					HEAD_OF_SECURITY =  SECURITY_PRINCIPAL_PERSON;
//				}
//				if(!SECURITY_PRINCIPAL_TELEPHONE.equals("")){
//					HOS_TEL = SECURITY_PRINCIPAL_TELEPHONE;					
//				}
//				if(!SECURITY_PRINCIPAL_MOBILE.equals("")){
//					HOS_MOBILE = SECURITY_PRINCIPAL_MOBILE;
//				}
				
				properties.put("HEAD_OF_SECURITY", SECURITY_PRINCIPAL_PERSON);// 安全负责人（50） ---  SECURITY_PRINCIPAL_PERSON（ T_COMPANY_SAFETY_PRODUCTION  ）
				properties.put("HOS_TEL", SECURITY_PRINCIPAL_TELEPHONE);//  安全负责人固定电话   ---   SECURITY_PRINCIPAL_TELEPHONE（ T_COMPANY_SAFETY_PRODUCTION  ）
				properties.put("HOS_MOBILE", SECURITY_PRINCIPAL_MOBILE);// 安全负责人移动电话  --- SECURITY_PRINCIPAL_MOBILE（ T_COMPANY_SAFETY_PRODUCTION  ）
				
				properties.put("HOS_EMAIL", "");// 安全负责人邮箱
				
				String REGISTERED_ADDRESS ="",PRODUCTION_ADDRESS="";
				String REG_ADDRESS = getObjValue(map.get("REG_ADDRESS"));
				String BUSINESS_ADDRESS = getObjValue(map.get("BUSINESS_ADDRESS"));
				
				if(!REG_ADDRESS.equals("")){
					REGISTERED_ADDRESS = REG_ADDRESS;
				}
				if(!BUSINESS_ADDRESS.equals("")){
					PRODUCTION_ADDRESS = BUSINESS_ADDRESS;
				}
				
				properties.put("REGISTERED_ADDRESS", REGISTERED_ADDRESS);//注册地址  --- REG_ADDRESS
				properties.put("PRODUCTION_ADDRESS", PRODUCTION_ADDRESS);//经营地址  --- BUSINESS_ADDRESS
				
				String  ZIP_CODE = getObjValue(map.get("ZIP_CODE"));
				
				if(!isnumber(ZIP_CODE) && ZIP_CODE.length() !=6){
					ZIP_CODE = "";
				}
				
				properties.put("ZIP_CODE", ZIP_CODE );//邮政编码 --- ZIP_CODE
				
				String ECONOMY_TYPE ="90",INDUSTRY_CATEGORY="AJ12";
				
				String ECONOMIC_TYPE2 = getObjValue(map.get("ECONOMIC_TYPE2"));
				String MANAGEMENT_LEVEL1 = getObjValue(map.get("MANAGEMENT_LEVEL1"));
				
				if(!ECONOMIC_TYPE2.equals("")){
					ECONOMY_TYPE = ECONOMIC_TYPE2;
				}
				if(!MANAGEMENT_LEVEL1.equals("")){
					INDUSTRY_CATEGORY = MANAGEMENT_LEVEL1;
				}
				
				properties.put("ECONOMY_TYPE", ECONOMY_TYPE );//经济类型 （其他：90）--    ECONOMIC_TYPE2
				properties.put("INDUSTRY_CATEGORY", INDUSTRY_CATEGORY);//【必填】所属行业（其他：AJ12） ----     MANAGEMENT_LEVEL1
				
				properties.put("MANAGE_AREA", getObjValue(map.get("BUSINESS_SCOPE")));//经营范围 ----  BUSINESS_SCOPE
				
				
				properties.put("INDUSTRY_CATEGORY_COUNTRY", "");//行业类别（国）  ---  MANAGEMENT_LEVEL1 --- 这个推送有问题？？？？？
				
				properties.put("SUBJECTION", "");// 企业行政隶属关系  ------- 默认
				properties.put("BUSINESS_STATUS", "");//企业经营状态 ----   默认
				
				//如果传递不是数字，置空
				String REG_CAPITAL = getObjValue(map.get("REG_CAPITAL"));
				String YEAR_SALES = getObjValue(map.get("YEAR_SALES"));
				if(!isnumber(REG_CAPITAL)){
					REG_CAPITAL ="";
				}
				
				if(!isnumber(YEAR_SALES)){
					YEAR_SALES ="";
				}
				
				properties.put("REGISTER_FUND", REG_CAPITAL );//注册资金   ---- REG_CAPITAL
				properties.put("SALE_INCOME", YEAR_SALES);//年收入 ---- YEAR_SALES
				properties.put("PROFIT", "");// 年利润     ---> 
				properties.put("TOTAL_MONEY", "");//资产总额    --->  
				
				
				properties.put("AREA", getObjValue(map.get("FLOOR_AREA")));//占地面积  ----> FLOOR_AREA
				properties.put("EMP_NUM", getObjValue(map.get("EMPLOYEE_ACTUAL_NUM")));//从业人员 ----  EMPLOYEE_ACTUAL_NUM
				properties.put("LICENSE_CATEGORY", "");// 营业执照类别 ----- 默认
				properties.put("IS_SCALE", "1");//是否规模以上 --- 默认
				properties.put("CO_SCALE", "");//企业规模 ----  默认
				properties.put("CO_LONGITUDE", getObjValue(map.get("LONGITUDE")));//企业经度 --- LONGITUDE
				properties.put("CO_LATITUDE", getObjValue(map.get("DIMENSIONALITY")));//企业纬度 --- DIMENSIONALITY
				properties.put("PARENT_ORG_NAME", "");//上级公司名称
				properties.put("GROUP_ORG_NAME", "");//集团公司名称
				properties.put("CO_NAME_OLD", "");//企业原名称
				properties.put("SAFTY_CATEGORY", "");// 安全类别  ------------  默认
				//查询最新的标准化,如果因为数据问题这里保证一条
				StringBuffer bsql = new StringBuffer();
				bsql.append("select GRADE from t_standard  where ");
				bsql.append(" validity_end = (select max(validity_end) from t_standard  where company_id = "+ID+")  and company_id = "+ID +" and is_deleted =0");
				Map<String,Object>  gRADEMap = new HashMap<String,Object>();
				List<Map<String,Object>> gRADEList = JdbcUtil.read(bsql.toString());
				if(gRADEList !=null && gRADEList.size() == 1){
					gRADEMap = gRADEList.get(0);
				}
				
				String WSS_LEVEL = "";
				String GRADE = getObjValue(gRADEMap.get("GRADE"));
				
				if(!GRADE.equals("")){
					WSS_LEVEL = "0" + GRADE.replace("NP_", "");
				}
				//logger.info("标准化等级："+WSS_LEVEL);
				properties.put("WSS_LEVEL", WSS_LEVEL);// 表准化等级  （01、02、03 、04规范化、05 未定级）-------  grade（NP_3）（t_standard）
				
				String INTEGRITY_LEVEL = "";
				String CREDIT_GRADE = getObjValue(map.get("CREDIT_GRADE"));
				if(CREDIT_GRADE.equals("A")){
					INTEGRITY_LEVEL = "01";
				} else if(CREDIT_GRADE.equals("B")){
					INTEGRITY_LEVEL = "02";
				} else if(CREDIT_GRADE.equals("C")){
					INTEGRITY_LEVEL = "03";
				} else if(CREDIT_GRADE.equals("D")){
					INTEGRITY_LEVEL = "04";
				} 
				if(WSS_LEVEL.equals("")){
					INTEGRITY_LEVEL = "05";
				}
				
				properties.put("INTEGRITY_LEVEL", INTEGRITY_LEVEL);//诚信等级  （01 A、02 B、03 C、04 D、05 未定级）------------- credit_grade(B)（t_credit_grade_change）
				properties.put("IS_BLACKLIST", "");// 是否诚信黑名单 --- 默认
				properties.put("IS_DEMONSTRATION", "");// 是否诚信示范企业 ----默认
				
				//14位即可  01  + 12位2级区域 01 +宁波市
				properties.put("LOCAL_WSB_CODE", "01"+SECOND_AREA+"000000");//属地安监机构代码 
				
				properties.put("REGULATORY_CATEGORY", "");//监管分类 ------ MANAGEMENT_LEVEL1
				properties.put("IS_MAJOR_HAZARD", "");//是否存在重大危险源

				ResponseDataE responseDataE = dataEx_COMPANY_BASIC_Service.edit_co_basic_info(COMPANY_NAME, properties,null);
				System.out.println(responseDataE.getInfo().getMessage());
				//推送失败，记录到同步失败表
				if(!responseDataE.getInfo().isOk()){
					System.out.println("同步返回状态失败---"+COMPANY_NAME);
				}
			}
		}
		
	}


	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
		
}
