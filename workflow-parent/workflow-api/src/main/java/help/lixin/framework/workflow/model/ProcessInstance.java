package help.lixin.framework.workflow.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ProcessInstance implements Serializable {
	private static final long serialVersionUID = -2905900018419595237L;
	private String id;
	private String name;
	private String deploymentId;
	private String description;
	private boolean isEnded;
	private boolean isSuspended;

	private String businessKey;

	private String processDefinitionId;
	private String processDefinitionKey;
	private String processDefinitionName;
	private Integer processDefinitionVersion;
	private String processInstanceId;

	private String referenceId;
	private String referenceType;
	private String superExecutionId;

	private String startActivityId;
	private Date startTime;
	private String startUserId;

	private String tenantId;
	private Map<String, Object> transientVariables;
	private Map<String, Object> variableInstances;

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private ProcessInstance instance = new ProcessInstance();

		public Builder id(String id) {
			instance.id = id;
			return this;
		}

		public Builder name(String name) {
			instance.name = name;
			return this;
		}

		public Builder businessKey(String businessKey) {
			instance.businessKey = businessKey;
			return this;
		}

		public Builder deploymentId(String deploymentId) {
			instance.deploymentId = deploymentId;
			return this;
		}

		public Builder description(String description) {
			instance.description = description;
			return this;
		}

		public Builder isEnded(boolean isEnded) {
			instance.isEnded = isEnded;
			return this;
		}

		public Builder isSuspended(boolean isSuspended) {
			instance.isSuspended = isSuspended;
			return this;
		}

		public Builder processDefinitionId(String processDefinitionId) {
			instance.processDefinitionId = processDefinitionId;
			return this;
		}

		public Builder processDefinitionKey(String processDefinitionKey) {
			instance.processDefinitionKey = processDefinitionKey;
			return this;
		}

		public Builder processDefinitionName(String processDefinitionName) {
			instance.processDefinitionName = processDefinitionName;
			return this;
		}

		public Builder processDefinitionVersion(Integer processDefinitionVersion) {
			instance.processDefinitionVersion = processDefinitionVersion;
			return this;
		}

		public Builder processInstanceId(String processInstanceId) {
			instance.processInstanceId = processInstanceId;
			return this;
		}

		public Builder startActivityId(String startActivityId) {
			instance.startActivityId = startActivityId;
			return this;
		}

		public Builder startTime(Date startTime) {
			instance.startTime = startTime;
			return this;
		}

		public Builder startUserId(String startUserId) {
			instance.startUserId = startUserId;
			return this;
		}

		public Builder tenantId(String tenantId) {
			instance.tenantId = tenantId;
			return this;
		}

		public Builder transientVariables(Map<String, Object> transientVariables) {
			instance.transientVariables = transientVariables;
			return this;
		}

		public Builder variableInstances(Map<String, Object> variableInstances) {
			instance.variableInstances = variableInstances;
			return this;
		}

		public Builder referenceId(String referenceId) {
			instance.referenceId = referenceId;
			return this;
		}

		public Builder referenceType(String referenceType) {
			instance.referenceType = referenceType;
			return this;
		}

		public Builder superExecutionId(String superExecutionId) {
			instance.superExecutionId = superExecutionId;
			return this;
		}

		public ProcessInstance build() {
			return instance;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public Integer getProcessDefinitionVersion() {
		return processDefinitionVersion;
	}

	public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
		this.processDefinitionVersion = processDefinitionVersion;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getStartActivityId() {
		return startActivityId;
	}

	public void setStartActivityId(String startActivityId) {
		this.startActivityId = startActivityId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Map<String, Object> getTransientVariables() {
		return transientVariables;
	}

	public void setTransientVariables(Map<String, Object> transientVariables) {
		this.transientVariables = transientVariables;
	}

	public Map<String, Object> getVariableInstances() {
		return variableInstances;
	}

	public void setVariableInstances(Map<String, Object> variableInstances) {
		this.variableInstances = variableInstances;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnded() {
		return isEnded;
	}

	public void setEnded(boolean isEnded) {
		this.isEnded = isEnded;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getSuperExecutionId() {
		return superExecutionId;
	}

	public void setSuperExecutionId(String superExecutionId) {
		this.superExecutionId = superExecutionId;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

}
