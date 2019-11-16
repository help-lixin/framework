package help.lixin.framework.test.service;

import java.util.List;
import java.util.Map;

public interface ILanguage {
	List<Map<String,Object>> queryAll();
	
	int save(Map<String,String> language);
}
