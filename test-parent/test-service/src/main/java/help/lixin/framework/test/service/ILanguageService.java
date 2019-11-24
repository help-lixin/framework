package help.lixin.framework.test.service;

import java.util.List;
import java.util.Map;

import help.lixin.framework.test.model.Language;

public interface ILanguageService {
	List<Map> queryAll();
	
	int save(Language language);
}
