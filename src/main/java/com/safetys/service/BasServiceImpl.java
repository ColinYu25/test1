package com.safetys.service;
import com.safetys.pagination.dialect.OracleDialect;
/**
 * @author zhaozhi3758
 */
public class BasServiceImpl extends OracleDialect implements IBasService {
	
//	@Autowired
//    @Qualifier("jdbcTemplate")
//    protected JdbcTemplate jdbcTemplate;
//	
//	public JdbcTemplate getJdbcTemplate() {
//		return jdbcTemplate;
//	}
//	
//	
//	/**
//	 * 分页查询
//	 */
//	@Override
//	public List<Map<String, Object>> queryForPage(String sql, int offset, int limit, Object... args) {
//		
//		return this.jdbcTemplate.queryForList(super.getLimitString(sql, offset, limit), args);
//	}

}
