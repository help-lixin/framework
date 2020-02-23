package help.lixin.framework.workflow.api;

import java.util.Map;

import help.lixin.framework.workflow.model.ProcessInstance;

public interface IProcessInstanceService {

	ProcessInstance startProcessInstanceByKey(String processDefinitionKey);

	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey);

	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables);

	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey,
			Map<String, Object> variables);

	ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String tenantId);

	ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String businessKey,
			String tenantId);

	ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, Map<String, Object> variables,
			String tenantId);

	ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String businessKey,
			Map<String, Object> variables, String tenantId);

	ProcessInstance startProcessInstanceById(String processDefinitionId);

	ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey);

	ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

	ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey,
			Map<String, Object> variables);

	ProcessInstance startProcessInstanceByMessage(String messageName);

	ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, String tenantId);

	ProcessInstance startProcessInstanceByMessage(String messageName, String businessKey);

	ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, String businessKey, String tenantId);

	ProcessInstance startProcessInstanceByMessage(String messageName, Map<String, Object> processVariables);

	ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, Map<String, Object> processVariables,
			String tenantId);

	ProcessInstance startProcessInstanceByMessage(String messageName, String businessKey,
			Map<String, Object> processVariables);

	ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, String businessKey,
			Map<String, Object> processVariables, String tenantId);

	void trigger(String executionId);

	void triggerAsync(String executionId);

	void trigger(String executionId, Map<String, Object> processVariables);

	void triggerAsync(String executionId, Map<String, Object> processVariables);

	void trigger(String executionId, Map<String, Object> processVariables, Map<String, Object> transientVariables);

	void updateBusinessKey(String processInstanceId, String businessKey);

	void addUserIdentityLink(String processInstanceId, String userId, String identityLinkType);

	void addGroupIdentityLink(String processInstanceId, String groupId, String identityLinkType);

	void addParticipantUser(String processInstanceId, String userId);

	void addParticipantGroup(String processInstanceId, String groupId);

	void deleteParticipantUser(String processInstanceId, String userId);

	void deleteParticipantGroup(String processInstanceId, String groupId);

	void deleteUserIdentityLink(String processInstanceId, String userId, String identityLinkType);

	void deleteGroupIdentityLink(String processInstanceId, String groupId, String identityLinkType);
}
