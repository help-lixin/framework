package help.lixin.framework.workflow.model;

public class ProcessDefinitionQueryCondition {
	private int index = 0;
	private int pageSize = 10;
	private String deploymentId;
	private String processDefinitionKey;
	private String processDefinitionId;
	// true:每一个流程只显示最后一个版本的
	private boolean latestVersion = true;

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		ProcessDefinitionQueryCondition condition = new ProcessDefinitionQueryCondition();

		public Builder deploymentId(String deploymentId) {
			condition.deploymentId = deploymentId;
			return this;
		}

		public Builder index(int index) {
			condition.index = index;
			return this;
		}

		public Builder pageSize(int pageSize) {
			condition.pageSize = pageSize;
			return this;
		}

		public Builder processDefinitionKey(String processDefinitionKey) {
			condition.processDefinitionKey = processDefinitionKey;
			return this;
		}

		public Builder processDefinitionId(String processDefinitionId) {
			condition.processDefinitionId = processDefinitionId;
			return this;
		}

		public Builder onLatestVersion() {
			condition.latestVersion = true;
			return this;
		}

		public Builder offLatestVersion() {
			condition.latestVersion = false;
			return this;
		}

		public ProcessDefinitionQueryCondition build() {
			if (condition.index < 0) {
				condition.index = 0;
			}
			if (condition.pageSize < 10) {
				condition.pageSize = 10;
			}
			return condition;
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public boolean isLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(boolean latestVersion) {
		this.latestVersion = latestVersion;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

}
