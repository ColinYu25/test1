package com.safetys.zwfw.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.text.StrBuilder;

import com.safetys.zwfw.dialect.Dialect;

public class Utils {
	
	public static final String C1 = "?";
	public static final String C2 = ",";
	public static final String C3 = "=";

	public static String StringFor(int length){
		StrBuilder s = new StrBuilder();
		for(int i=0;i<length;i++){
			if(i>0){
				s.append(C2);
			}
			s.append(C1);
		}
		return s.toString();
	}
	
	public static String getSetValue(String[] columns,List<String> exclude){
		List<String> cols = exclude(columns, exclude);
		StrBuilder s = new StrBuilder();
		for(int i=0;i<cols.size();i++){
			if(s.length()>0){
				s.append(C2);
			}
			s.append(cols.get(i)).append(C3);
			s.append(C1);
		}
		return s.toString();
	}
	public static String getSetValue(String[] columns,List<String> exclude,Dialect dialect){
		List<String> cols = exclude(columns, exclude);
		StrBuilder s = new StrBuilder();
		for(int i=0;i<cols.size();i++){
			if(s.length()>0){
				s.append(C2);
			}
			s.append(dialect.convertColumnName(cols.get(i))).append(C3);
			s.append(C1);
		}
		return s.toString();
	}
	public static String getSelectColumns(String[] columns,Dialect dialect){
		List<String> cols = new ArrayList<String>();
		for(String column : columns){
			cols.add(dialect.convertColumnName(column));
		}
		return StringUtils.join(cols,",");
	}
	
	public static List<String> exclude(String[] columns,List<String> exclude){
		List<String> result = new ArrayList<String>();
		for(int i=0;i<columns.length;i++){
			if(exclude.contains(columns[i])){
				continue;
			}
			result.add(columns[i]);
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		String str= "SYNC_DATA_ABVDB";
		System.out.println(Pattern.matches("^SYNC_(DATA|SOURCE|COVER)_\\w*", str));
	}
}
