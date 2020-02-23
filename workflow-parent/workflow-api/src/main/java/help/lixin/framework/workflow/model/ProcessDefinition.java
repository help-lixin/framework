package help.lixin.framework.workflow.model;

import java.io.Serializable;

public class ProcessDefinition implements Serializable {
	private static final long serialVersionUID = -3014384005429996449L;
	private String deploymentId;
	private String category;
	private String id;
	private String key;
	private String name;
	private int version;
	private String description;
	private String tenantId;
	private Boolean suspended;
	
	
	public static Builder newBuilder(){
		return new Builder();
	}

	public static class Builder {
		private ProcessDefinition processDefinition = new ProcessDefinition();

		public Builder deploymentId(String deploymentId) {
			processDefinition.deploymentId = deploymentId;
			return this;
		}

		public Builder category(String category) {
			processDefinition.category = category;
			return this;
		}

		public Builder id(String id) {
			processDefinition.id = id;
			return this;
		}

		public Builder key(String key) {
			processDefinition.key = key;
			return this;
		}

		public Builder name(String name) {
			processDefinition.name = name;
			return this;
		}

		public Builder version(int version) {
			processDefinition.version = version;
			return this;
		}

		public Builder description(String description) {
			processDefinition.description = description;
			return this;
		}

		public Builder tenantId(String tenantId) {
			processDefinition.tenantId = tenantId;
			return this;
		}

		public Builder suspended(boolean suspended) {
			processDefinition.suspended = suspended;
			return this;
		}

		public ProcessDefinition build() {
			return processDefinition;
		}

	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Boolean getSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}
}
