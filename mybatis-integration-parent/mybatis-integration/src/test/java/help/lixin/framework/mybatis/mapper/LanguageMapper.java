package help.lixin.framework.mybatis.mapper;

import java.util.List;
import java.util.Map;

import help.lixin.framework.mybatis.model.Language;

public interface LanguageMapper {

	List<Map> query(String id);
	
	int save(Language lanugage);
}
