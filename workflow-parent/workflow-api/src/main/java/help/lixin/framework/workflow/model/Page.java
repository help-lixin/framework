package help.lixin.framework.workflow.model;

import java.io.Serializable;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = -3328188576919171544L;
	private int index = 1;
	private int pageSize = 10;
	private long totalPage;
	private T data;

	public static <T> Builder<T> newBuilder() {
		return new Builder<T>();
	}

	public static class Builder<T> {
		Page<T> page = new Page<T>();

		public Builder<T> index(int index) {
			page.index = index;
			return this;
		}

		public Builder<T> pageSize(int pageSize) {
			page.pageSize = pageSize;
			return this;
		}

		public Builder<T> totalPage(long totalPage) {
			page.totalPage = totalPage;
			return this;
		}

		public Builder<T> data(T data) {
			page.data = data;
			return this;
		}

		public Page<T> build() {
			return page;
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

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
