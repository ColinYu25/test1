package com.safetys.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import cn.safetys.center.db.cxf.WsLoader;
import cn.safetys.center.db.cxf.xzxk.IXZXK;
/**
 * jdbc 工具类，可借助此完成一些简单的数据库初始化操作
 * @author zhaozhi3758
 */
public class JdbcUtil {
	
	private static final String URL = "jdbc:oracle:thin:@60.190.2.249:1521:orcl";
	private static final String USER = "CENTER_DB_TEST";
	private static final String PASSWORD = "CENTER_DB_TEST_QWER249";
	
	private static JdbcUtil instance = null;
	
	private JdbcUtil(){}
	
	public static JdbcUtil getInstance() {
		if (instance == null) {
			synchronized (JdbcUtil.class) {
				if (instance == null) {
					instance = new JdbcUtil();
				}
			}
		}
		return instance;
	}

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	//插入或者更新数据
	public static void update(String sql) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(null, stmt, conn);
		}
	}
	
	//批量更新
	public static void updateBatch(String sqls[]) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(String sql : sqls){
				stmt.addBatch(sql);
			}
			stmt.executeBatch();  
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(null, stmt, conn);
		}
	}
	
	//读取数据
	public static List<Map<String, Object>> read(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			String[] colNames = new String[count];
			for (int i = 1; i <= count; i++) {
				colNames[i-1] = rsmd.getColumnLabel(i);
			}
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			while (rs.next()) {
				Map<String, Object> data = new HashMap<String, Object>();
				for (int i = 0; i < colNames.length; i++) {
					data.put(colNames[i], rs.getObject(colNames[i]));
				}
				datas.add(data);
			}
			return datas;
		} finally {
			free(rs, ps, conn);
		}
	}
	
	
	private static void convertMapToJson(Map<String, Object> map,List<cn.safetys.center.db.cxf.xzxk.MapEntry> ers){
		
		try {
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry =  (Map.Entry) it.next();
				cn.safetys.center.db.cxf.xzxk.MapEntry mapEntry = new cn.safetys.center.db.cxf.xzxk.MapEntry();
				String key = entry.getKey().toString();
				Object obj = entry.getValue();
				if (obj == null) {
					continue;
				} else if (obj instanceof Date) {
					mapEntry.setKey(key);// 字段名
					mapEntry.setValue(DateUtil.dateFormat((Date) obj));// 字段值
					ers.add(mapEntry); 
				} else {
					mapEntry.setKey(key);// 字段名
					mapEntry.setValue(obj.toString());// 字段值
					ers.add(mapEntry);  
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		String sql = "select l.*,c.uuid  from T_LICENSE l left join t_company c on l.company_id = c.id  where l.non_coal_type ='mine_4'";
		List< Map<String, Object> > rdatas = read(sql);
		
//		System.out.println(rdatas);
		for( Map<String, Object> map :  rdatas ){
			String uuid = map.get("UUID").toString();
			Long  xkId = Long.parseLong(map.get("XK_ID").toString());
			cn.safetys.center.db.cxf.xzxk.MapConvertor convertor = new cn.safetys.center.db.cxf.xzxk.MapConvertor();
			List<cn.safetys.center.db.cxf.xzxk.MapEntry> ers = convertor.getEntries();
			convertMapToJson(map,ers);
			System.out.println(WsLoader.getInterface(IXZXK.class).modifyXZXKInfo("#NBXZXK#2013#", uuid, xkId,convertor));
		}
		
	}
	
	
	
	
	
	
	
	

}
