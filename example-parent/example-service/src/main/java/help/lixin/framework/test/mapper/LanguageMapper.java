package help.lixin.framework.test.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import help.lixin.framework.test.model.Language;

@Mapper
public interface LanguageMapper {

	List<Map> query();
	
	int save(Language lanugage);
}
