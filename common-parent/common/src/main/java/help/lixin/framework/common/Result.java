package help.lixin.framework.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果集的封装.
 * 
 * @author lixin
 */
public class Result<T> implements Serializable {
	private static final long serialVersionUID = -8754624853072375484L;
	private int code;
	private String message;
	private T data;
	private Map<Object, Object> others = new HashMap<>();

	public static <T> Builder<T> newBuilder() {
		return new Builder<T>();
	}

	public static class Builder<T> {
		private Result<T> result = new Result<T>();

		public Builder<T> code(int code) {
			this.result.code = code;
			return this;
		}

		public Builder<T> message(String message) {
			this.result.message = message;
			return this;
		}

		public Builder<T> data(T data) {
			this.result.data = data;
			return this;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Builder<T> others(Map others) {
			if (null != others && !others.isEmpty()) {
				this.result.others.putAll(others);
			}
			return this;
		}

		public Builder<T> other(Object key, Object value) {
			if (null != key) {
				this.result.others.put(key, value);
			}
			return this;
		}

		public Result<T> build() {
			return result;
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Map<Object, Object> getOthers() {
		return others;
	}

	public void setOthers(Map<Object, Object> others) {
		this.others = others;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", data=" + data + ", others=" + others + "]";
	}
}
