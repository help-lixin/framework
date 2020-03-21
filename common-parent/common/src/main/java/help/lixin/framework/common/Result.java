package help.lixin.framework.common;

import java.io.Serializable;

/**
 * 返回结果集的封装.
 * 
 * @author lixin
 *
 */
public class Result implements Serializable {
	private static final long serialVersionUID = -8754624853072375484L;
	// 403
	private int status;
	// Forbidden
	private String error;
	// Access is denied
	private String message;
	// /userLogin.do
	private String path;
	// 1584197721312
	private long timestamp;

	public static class Builder {
		private Result result = new Result();

		// 403
		public Builder status(int status) {
			this.result.status = status;
			return this;
		}

		// Forbidden
		public Builder error(String error) {
			this.result.error = error;
			return this;
		}

		// Access is denied
		public Builder message(String message) {
			this.result.message = message;
			return this;
		}

		// /userLogin.do
		public Builder path(String path) {
			this.result.path = path;
			return this;
		}

		// 1584197721312
		public Builder timestamp(long timestamp) {
			this.result.timestamp = timestamp;
			return this;
		}

		public Result build() {
			return result;
		}

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Result [status=" + status + ", error=" + error + ", message=" + message + ", path=" + path
				+ ", timestamp=" + timestamp + "]";
	}
}
