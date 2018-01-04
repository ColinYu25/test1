package com.safetys.syn;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 公共事件基类
 * @author zhaozhi3758
 */
public abstract class BaseEvent  implements IEvent{
	
	protected static final String SQL_END_DELIMITER = ";";
	public static final String EMPTY = "";
	public static final int INDEX_NOT_FOUND = -1;
	public static final int  MAX_THREAD_NUM = 5;
	
	
	public static String getCountString(String sql) {
		sql = trim(sql);
		String totalSql = sql.toUpperCase();
		totalSql = "SELECT COUNT(1) FROM " + substringAfter(totalSql, "FROM");
		totalSql = substringBefore(totalSql, "ORDER BY");
		totalSql = substringBefore(totalSql, "GROUP BY");

		return totalSql;
	}
	
	public static String getLimitString(String sql, int start, int limit) {
		if (start > 0) {
			return sql + " limit " + start + "," + limit;
		} else {
			return sql + " limit " + limit;
		}
	}
	
	public static  synchronized void  batchUpdate(JdbcTemplate jdbcTemplate,String sql,List <Object[]> batchArgs){
		if(batchArgs.size() == 0) return ;
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	
	private static String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0,
					sql.length() - 1 - SQL_END_DELIMITER.length());
		}
		return sql;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String substringBefore(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}
	
	public  static String getObjValue(Object obj){
		if(obj == null ){
			return  "";
		} else {
			return obj.toString();
		}
	}
	
	
	public  static Object getValue(Object obj){
		if(obj == null ){
			return  "";
		} else {
			if(obj instanceof Date){
				return new java.sql.Date(((Date) obj).getTime());
			} else {
				return obj;
			}
		}
	}
	
	public static boolean regex(String reg, String str) {
		Pattern pat = Pattern.compile(reg);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find()) {
			flg = true;
		}
		return flg;
	}
	
	
	public static boolean ismobile(String str) {
		return regex("^((\\(\\d{2,3}\\))|(\\d{3}\\-))?((13\\d{9})|(15[^4]\\d{8})|(18[^4]\\d{8})|(14[7]\\d{8}))$", str);
	}
	
	
	public static boolean isnumber(String str) {
		return regex("^\\d+\\.{0,1}\\d*$", str);
	}
	
	public static boolean isChinese(String str) {
		return regex("[\u4e00-\u9fa5]", str);
	}

	public static boolean is15number(String str) {
		return regex("^[0-9]{15}$", str);
	}

	public static boolean is13number(String str) {
		return regex("^[0-9]{13}$", str);
	}
	
	
	//15位纯数字或者13位纯数字或者含有汉字的工商注册号允许通过
	public static boolean isNotRegNumber(String str){
		if(str == null ) return true;
		if( is13number(str) || is15number(str) || isChinese(str )){
			return false;
		} else {
			return true;
		}
	}
	
	//获取行政区划
	public static String getBeforeNum(String  str){
		Pattern pattern = Pattern.compile("([0|1-9]+[1-9])0+");
	    Matcher matcher = pattern.matcher(str);
	    if (matcher.find()) {
	    	return matcher.group(1);
	    }
		return null;
	}
	
    public static String clobToString(Clob clob) {
	        Reader is = null;
	        BufferedReader br = null;
	        StringBuffer sb = new StringBuffer();
	        try {
	            is = clob.getCharacterStream();
	            br = new BufferedReader(is);
	            String s = null;
	            s = br.readLine();
	            while (s != null) {
	                sb.append(s);
	                s = br.readLine();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (br != null) {
	                    br.close();
	                }
	                if (is != null) {
	                    is.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
    
    public String getLimitString4Oralce(String sql, int offset, int limit) {
		sql = trim(sql);

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
			pagingSelect.append(" ) row_ where rownum <= ")
					.append(offset + limit).append(" ) where rownum_ > ")
					.append(offset);
		} else {
			pagingSelect.append(" ) where rownum <= ").append(offset + limit);
		}

		return pagingSelect.toString();
	}
}
