package help.lixin.framework.test.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import help.lixin.framework.dynamic.datasource.context.DataSourceContext;
import help.lixin.framework.test.service.ILanguage;

@Service
@Transactional
public class LanguageJdbcTemplate implements ILanguage {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> queryAll() {
		String sql = "SELECT * FROM fw_language";
		// 设置数据源名称
		DataSourceContext.set("test");
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		return resultList;
	}

	@Override
	public int save(Map<String, String> language) {
		String sql = "INSERT INTO fw_language(name,code,enabled_flag) VALUES(?,?,?)";
		// 自动commit
		int result = jdbcTemplate.update(sql, new Object[] { "lixin", "lixin", "Y" });
		result = jdbcTemplate.update(sql, new Object[] { "test", "test", "N" });
//		throw new RuntimeException("test");
		return result;
	}
}
