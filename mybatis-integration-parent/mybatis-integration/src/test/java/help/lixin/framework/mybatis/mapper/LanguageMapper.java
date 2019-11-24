package help.lixin.framework.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import help.lixin.framework.mybatis.model.Language;

@Mapper
public interface LanguageMapper {

	List<Map> query(String id);
	
	int save(Language lanugage);
}
