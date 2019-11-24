package help.lixin.framework.test.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import help.lixin.framework.mybatis.integration.context.VarContext;
import help.lixin.framework.test.mapper.LanguageMapper;
import help.lixin.framework.test.model.Language;
import help.lixin.framework.test.service.ILanguageService;

@Service
public class LanguageServiceMyBatis implements ILanguageService {

	@Resource
	private LanguageMapper languageMapper;

	@Override
	public List<Map> queryAll() {
		// 手动设置DB需要的信息
		new VarContext.Builder()
		.var("_db","fw")
		.var("_tablePrefix","fw_")
		.bind();
		return languageMapper.query();
	}

	@Override
	public int save(Language language) {
		// 手动设置DB需要的信息
		new VarContext.Builder()
		.var("_db","fw")
		.var("_tablePrefix","fw_")
		.bind();
		return languageMapper.save(language);
	}
}
