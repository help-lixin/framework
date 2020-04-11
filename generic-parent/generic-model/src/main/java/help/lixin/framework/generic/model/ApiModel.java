package help.lixin.framework.generic.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiModel {

	private String value;

	private String description;

	private Map<String, String> extProperties = new LinkedHashMap<String, String>();

	private List<ApiOperationModel> apiOperations = new ArrayList<>(1);

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getExtProperties() {
		return extProperties;
	}

	public void setExtProperties(Map<String, String> extProperties) {
		this.extProperties = extProperties;
	}

	public List<ApiOperationModel> getApiOperations() {
		return apiOperations;
	}

	public void setApiOperations(List<ApiOperationModel> apiOperations) {
		this.apiOperations = apiOperations;
	}
	
}
