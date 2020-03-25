package help.lixin.framework.power.meta.model;

import java.io.Serializable;

public class SystemModel implements Serializable {
	private static final long serialVersionUID = 7200647607475859022L;
	/**
	 * 系统ID<br/>
	 * 如果指定了id,则使用ID.<br/>
	 * 如果没有指定id,则使用:name+code进行md5产生ID<br/>
	 */
	private String id;

	/**
	 * 系统名称
	 */
	private String name;

	/**
	 * 系统编码
	 */
	private String code;

	/**
	 * 所属系统的父ID
	 */
	private String parentId = "-1";

	/**
	 * 系统描述
	 */
	private String desc;

	/**
	 * 顺序
	 */
	private int order = 1;

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private SystemModel model = new SystemModel();

		/**
		 * 系统ID<br/>
		 * 如果指定了id,则使用ID.<br/>
		 * 如果没有指定id,则使用:name+code进行md5产生ID<br/>
		 * 
		 * @return
		 */
		public Builder id(String id) {
			model.id = id;
			return this;
		}

		/**
		 * 系统名称
		 * 
		 * @return
		 */
		public Builder name(String name) {
			model.name = name;
			return this;
		}

		/**
		 * 系统编码
		 * 
		 * @return
		 */
		public Builder code(String code) {
			model.code = code;
			return this;
		}

		/**
		 * 所属系统的父ID
		 * 
		 * @return
		 */
		public Builder parentId(String parentId) {
			model.parentId = parentId;
			return this;
		}

		/**
		 * 系统描述
		 * 
		 * @return
		 */
		public Builder desc(String desc) {
			model.desc = desc;
			return this;
		}

		/**
		 * 顺序
		 * 
		 * @return
		 */
		public Builder order(int order) {
			model.order = order;
			return this;
		}

		public SystemModel build() {
			return model;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemModel other = (SystemModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SystemModel [id=" + id + ", name=" + name + ", code=" + code + ", parentId=" + parentId + ", desc="
				+ desc + ", order=" + order + "]";
	}

}
