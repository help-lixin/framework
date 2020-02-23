package help.lixin.framework.flowable.service.impl;

import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import help.lixin.framework.workflow.api.IProcessInstanceService;
import help.lixin.framework.workflow.model.ProcessInstance;


@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ProcessInstanceService implements IProcessInstanceService {

	private RuntimeService runtimeService;

	public ProcessInstanceService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey) {
		return conver(runtimeService.startProcessInstanceByKey(processDefinitionKey));
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey) {
		return conver(runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey));
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) {
		return conver(runtimeService.startProcessInstanceByKey(processDefinitionKey, variables));
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey,
			Map<String, Object> variables) {
		return conver(runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables));
	}

	@Override
	public ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String tenantId) {
		return conver(runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, tenantId));
	}

	@Override
	public ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String businessKey,
			String tenantId) {
		return conver(runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey, tenantId));
	}

	@Override
	public ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey,
			Map<String, Object> variables, String tenantId) {
		return conver(runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, variables, tenantId));
	}

	@Override
	public ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String businessKey,
			Map<String, Object> variables, String tenantId) {
		return conver(runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey, variables,
				tenantId));
	}

	@Override
	public ProcessInstance startProcessInstanceById(String processDefinitionId) {
		return conver(runtimeService.startProcessInstanceById(processDefinitionId));
	}

	@Override
	public ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey) {
		return conver(runtimeService.startProcessInstanceById(processDefinitionId, businessKey));
	}

	@Override
	public ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) {
		return conver(runtimeService.startProcessInstanceById(processDefinitionId, variables));
	}

	@Override
	public ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey,
			Map<String, Object> variables) {
		return conver(runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables));
	}

	@Override
	public ProcessInstance startProcessInstanceByMessage(String messageName) {
		return conver(runtimeService.startProcessInstanceByMessage(messageName));
	}

	@Override
	public ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, String tenantId) {
		return conver(runtimeService.startProcessInstanceByMessageAndTenantId(messageName, tenantId));
	}

	@Override
	public ProcessInstance startProcessInstanceByMessage(String messageName, String businessKey) {
		return conver(runtimeService.startProcessInstanceByMessage(messageName, businessKey));
	}

	@Override
	public ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, String businessKey,
			String tenantId) {
		return conver(runtimeService.startProcessInstanceByMessageAndTenantId(messageName, businessKey, tenantId));
	}

	@Override
	public ProcessInstance startProcessInstanceByMessage(String messageName, Map<String, Object> processVariables) {
		return conver(runtimeService.startProcessInstanceByMessage(messageName, processVariables));
	}

	@Override
	public ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName,
			Map<String, Object> processVariables, String tenantId) {
		return conver(runtimeService.startProcessInstanceByMessageAndTenantId(messageName, processVariables, tenantId));
	}

	@Override
	public ProcessInstance startProcessInstanceByMessage(String messageName, String businessKey,
			Map<String, Object> processVariables) {
		return conver(runtimeService.startProcessInstanceByMessage(messageName, businessKey, processVariables));
	}

	@Override
	public ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, String businessKey,
			Map<String, Object> processVariables, String tenantId) {
		return conver(runtimeService.startProcessInstanceByMessageAndTenantId(messageName, businessKey,
				processVariables, tenantId));
	}

	@Override
	public void trigger(String executionId) {
		runtimeService.trigger(executionId);
	}

	@Override
	public void triggerAsync(String executionId) {
		runtimeService.triggerAsync(executionId);
	}

	@Override
	public void trigger(String executionId, Map<String, Object> processVariables) {
		runtimeService.trigger(executionId, processVariables);
	}

	@Override
	public void triggerAsync(String executionId, Map<String, Object> processVariables) {
		runtimeService.triggerAsync(executionId, processVariables);
	}

	@Override
	public void trigger(String executionId, Map<String, Object> processVariables,
			Map<String, Object> transientVariables) {
		runtimeService.trigger(executionId, processVariables, transientVariables);
	}

	@Override
	public void updateBusinessKey(String processInstanceId, String businessKey) {
		runtimeService.updateBusinessKey(processInstanceId, businessKey);
	}

	@Override
	public void addUserIdentityLink(String processInstanceId, String userId, String identityLinkType) {
		runtimeService.addUserIdentityLink(processInstanceId, userId, identityLinkType);
	}

	@Override
	public void addGroupIdentityLink(String processInstanceId, String groupId, String identityLinkType) {
		runtimeService.addGroupIdentityLink(processInstanceId, groupId, identityLinkType);
	}

	@Override
	public void addParticipantUser(String processInstanceId, String userId) {
		runtimeService.addParticipantUser(processInstanceId, userId);
	}

	@Override
	public void addParticipantGroup(String processInstanceId, String groupId) {
		runtimeService.addParticipantGroup(processInstanceId, groupId);
	}

	@Override
	public void deleteParticipantUser(String processInstanceId, String userId) {
		runtimeService.deleteParticipantUser(processInstanceId, userId);
	}

	@Override
	public void deleteParticipantGroup(String processInstanceId, String groupId) {
		runtimeService.deleteParticipantGroup(processInstanceId, groupId);
	}

	@Override
	public void deleteUserIdentityLink(String processInstanceId, String userId, String identityLinkType) {
		runtimeService.deleteUserIdentityLink(processInstanceId, userId, identityLinkType);
	}

	@Override
	public void deleteGroupIdentityLink(String processInstanceId, String groupId, String identityLinkType) {
		runtimeService.deleteGroupIdentityLink(processInstanceId, groupId, identityLinkType);
	}

	private ProcessInstance conver(org.flowable.engine.runtime.ProcessInstance instanceTmp) {
		if (null != instanceTmp) {
			return ProcessInstance.newBuilder() //
					.startActivityId(instanceTmp.getActivityId()) //
					.businessKey(instanceTmp.getBusinessKey()) //
					.id(instanceTmp.getId()) //
					.name(instanceTmp.getName()) //
					.deploymentId(instanceTmp.getDeploymentId()) //
					.description(instanceTmp.getDescription()) //

					.processDefinitionId(instanceTmp.getProcessDefinitionId()) //
					.processDefinitionKey(instanceTmp.getProcessDefinitionKey()) //
					.processDefinitionName(instanceTmp.getProcessDefinitionName()) //
					.processDefinitionVersion(instanceTmp.getProcessDefinitionVersion()) //
					.processInstanceId(instanceTmp.getProcessInstanceId()) //
					.variableInstances(instanceTmp.getProcessVariables()) //

					.tenantId(instanceTmp.getTenantId()) //
					.startTime(instanceTmp.getStartTime()) //
					.startUserId(instanceTmp.getStartUserId()) //
					.isEnded(instanceTmp.isEnded()) //
					.isSuspended(instanceTmp.isSuspended()) //

					.referenceId(instanceTmp.getReferenceId()) //
					.referenceType(instanceTmp.getReferenceType()) //
					.superExecutionId(instanceTmp.getSuperExecutionId()) //

					.build();

		}
		return null;
	}

}
