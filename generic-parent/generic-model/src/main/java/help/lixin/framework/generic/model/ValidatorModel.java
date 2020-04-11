package help.lixin.framework.generic.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ValidatorModel implements Serializable {
	private static final long serialVersionUID = -7466186478044438907L;

	private String name;

	private String description;

	private String rule;

	private String message;

	private Map<String, String> extProperties = new HashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getExtProperties() {
		return extProperties;
	}

	public void setExtProperties(Map<String, String> extProperties) {
		this.extProperties = extProperties;
	}
}
