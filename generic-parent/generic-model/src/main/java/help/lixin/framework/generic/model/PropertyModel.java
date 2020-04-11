package help.lixin.framework.generic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PropertyModel implements Serializable {
	private static final long serialVersionUID = 8602694446598465055L;

	private String name;

	private String value;

	private String paramType;

	private boolean required;

	private boolean hidden;

	private String dataType;

	private String example;

	// 列定义信息(生成表结构和验证框架无法集成一起,因为:验证和API接口都能支持多层嵌套,而表结构是平面的不存在嵌套)
	// private ColumnModel columnModel;

	// 所有的验证信息
	private List<ValidatorModel> validators = new ArrayList<>(1);

	// 扩展信息
	private Map<String, String> extProperties = new LinkedHashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	// public ColumnModel getColumnModel() {
	// return columnModel;
	// }
	//
	// public void setColumnModel(ColumnModel columnModel) {
	// this.columnModel = columnModel;
	// }

	public List<ValidatorModel> getValidators() {
		return validators;
	}

	public void addValidator(ValidatorModel validator) {
		getValidators().add(validator);
	}

	public Map<String, String> getExtProperties() {
		return extProperties;
	}

	public void addExtProperties(String name, String value) {
		getExtProperties().put(name, value);
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
}
