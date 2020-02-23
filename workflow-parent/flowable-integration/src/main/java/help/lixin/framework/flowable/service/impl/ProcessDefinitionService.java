package help.lixin.framework.flowable.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinitionQuery;

import help.lixin.framework.workflow.api.IProcessDefinitionService;
import help.lixin.framework.workflow.model.Page;
import help.lixin.framework.workflow.model.ProcessDefinition;
import help.lixin.framework.workflow.model.ProcessDefinitionQueryCondition;

public class ProcessDefinitionService implements IProcessDefinitionService {

	private RepositoryService repositoryService;

	public ProcessDefinitionService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Override
	public String deploy(String resourceName, String text) {
		Deployment deployment = repositoryService.createDeployment() //
				.addString(resourceName, text) //
				.deploy();
		return deployment.getId();
	}

	@Override
	public String deploy(String resourceName, byte[] bytes) {
		Deployment deployment = repositoryService.createDeployment() //
				.addBytes(resourceName, bytes) //
				.deploy();
		return deployment.getId();
	}

	@Override
	public String deploy(String resourceName, InputStream inputStream) {
		Deployment deployment = repositoryService.createDeployment() //
				.addInputStream(resourceName, inputStream) //
				.deploy();
		return deployment.getId();
	}

	@Override
	public String deploy(ZipInputStream zipInputStream) {
		Deployment deployment = repositoryService.createDeployment() //
				.addZipInputStream(zipInputStream) //
				.deploy();
		return deployment.getId();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page list(ProcessDefinitionQueryCondition condition) {
		// 构建查询参数
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery(); //
		// 流程部署后的ID
		if (StringUtils.isNotBlank(condition.getDeploymentId())) {
			processDefinitionQuery.deploymentId(condition.getDeploymentId());
		}
		// 流程定义的ID
		if (StringUtils.isNotBlank(condition.getProcessDefinitionId())) {
			processDefinitionQuery.processDefinitionId(condition.getProcessDefinitionId());
		}
		// 流程定义的KEY
		if (StringUtils.isNotBlank(condition.getProcessDefinitionKey())) {
			processDefinitionQuery.processDefinitionKeyLike(condition.getProcessDefinitionKey());
		}
		// 是否只显示最后一个版本
		if (condition.isLatestVersion()) {
			processDefinitionQuery.latestVersion();
		}
		// 统计有多少条记录
		long count = processDefinitionQuery.count();
		// 总记录数 / 每显需要求显示的数量
		long totalPage = (count / condition.getPageSize()) < 1 //
				? ((count / condition.getPageSize()) + 1) //
				: (count / condition.getPageSize());
		// 查询工作流引擎
		List<org.flowable.engine.repository.ProcessDefinition> queryResult = //
				processDefinitionQuery.listPage(condition.getIndex(), condition.getPageSize());
		return Page.newBuilder() //
				.index(condition.getIndex()) //
				.pageSize(condition.getPageSize()) //
				.totalPage(totalPage) //
				.data(converList(queryResult)) //
				.build();
	}

	@Override
	public ProcessDefinition get(String processDefinitionId) {
		org.flowable.engine.repository.ProcessDefinition result = //
				repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
						.singleResult();
		return conver(result);
	}

	private List<ProcessDefinition> converList(List<org.flowable.engine.repository.ProcessDefinition> queryResult) {
		List<ProcessDefinition> result = new ArrayList<ProcessDefinition>(0);
		if (null != queryResult) {
			for (org.flowable.engine.repository.ProcessDefinition queryProcessDefinition : queryResult) {
				ProcessDefinition processDefinition = conver(queryProcessDefinition);
				if (null != processDefinition) {
					result.add(processDefinition);
				}
			}
		}
		return result;
	}

	private ProcessDefinition conver(org.flowable.engine.repository.ProcessDefinition queryResult) {
		if (null != queryResult) {
			return ProcessDefinition.newBuilder() //
					.id(queryResult.getId()) //
					.key(queryResult.getKey()) //
					.name(queryResult.getName()) //
					.version(queryResult.getVersion()) //
					.deploymentId(queryResult.getDescription()) //
					.tenantId(queryResult.getTenantId()) //
					.suspended(queryResult.isSuspended()) //
					.deploymentId(queryResult.getDeploymentId()) //
					.category(queryResult.getCategory()) //
					.build();
		}
		return null;
	}

}
