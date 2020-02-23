package help.lixin.framework.workflow.api;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import help.lixin.framework.workflow.model.Page;
import help.lixin.framework.workflow.model.ProcessDefinition;
import help.lixin.framework.workflow.model.ProcessDefinitionQueryCondition;

/**
 * 流程定义信息管理
 * 
 * @author lixin
 */
public interface IProcessDefinitionService {

	String deploy(String resourceName, String text);

	String deploy(String resourceName, byte[] bytes);

	String deploy(String resourceName, InputStream inputStream);

	String deploy(ZipInputStream zipInputStream);

	Page<List<ProcessDefinition>> list(ProcessDefinitionQueryCondition condition);

	ProcessDefinition get(String processDefinitionId);
}
