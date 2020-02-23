package help.lixin.framework.flowable.boot;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import help.lixin.framework.flowable.service.impl.ProcessDefinitionService;
import help.lixin.framework.flowable.service.impl.ProcessInstanceService;
import help.lixin.framework.workflow.api.IProcessDefinitionService;
import help.lixin.framework.workflow.api.IProcessInstanceService;

@Configuration
@AutoConfigureAfter({ ProcessEngineServicesAutoConfiguration.class })
public class WorkFlowConfiguration {

	/**
	 * 流程部署管理
	 * 
	 * @param repositoryService
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public IProcessDefinitionService workFlowDeployService(RepositoryService repositoryService) {
		IProcessDefinitionService processDefinitionService = new ProcessDefinitionService(repositoryService);
		return processDefinitionService;
	}

	/**
	 * 流程实例管理
	 * 
	 * @param runtimeService
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public IProcessInstanceService processInstanceService(RuntimeService runtimeService) {
		IProcessInstanceService processInstanceService = new ProcessInstanceService(runtimeService);
		return processInstanceService;
	}
	
}
