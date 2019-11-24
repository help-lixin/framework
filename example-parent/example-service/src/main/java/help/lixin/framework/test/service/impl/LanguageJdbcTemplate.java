package help.lixin.framework.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import help.lixin.framework.test.model.Language;
import help.lixin.framework.test.service.ILanguageService;

@Service
@Transactional
public class LanguageJdbcTemplate implements ILanguageService {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map> queryAll() {
		String sql = "SELECT * FROM fw_language";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Map> result = new ArrayList<>();
		result.addAll(resultList);
		return result;
	}

	@Override
	public int save(Language language) {
		String sql = "INSERT INTO fw_language(id,name,code) VALUES(?,?,?)";
		// 自动commit
		int result = jdbcTemplate.update(sql,
				new Object[] { language.getId(), language.getName(), language.getCode() });
		// throw new RuntimeException("test");
		return result;
	}
}
