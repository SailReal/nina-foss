package de.ninafoss.domain;

import java.io.Serializable;

public class Location implements Serializable {

	private final Long id;
	private final String code;
	private final String name;

	private Location(Builder builder) {
		this.id = builder.id;
		this.code = builder.code;
		this.name = builder.name;
	}

	public static Builder aLocation() {
		return new Builder();
	}

	public static Builder aCopyOf(Location location) {
		return new Builder().withId(location.id()).withCode(location.code()).withName(location.name());
	}

	public Long id() {
		return id;
	}

	public String code() {
		return code;
	}

	public String name() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		return internalEquals((Location) obj);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	private boolean internalEquals(Location obj) {
		return id != null && id.equals(obj.id);
	}

	public static class Builder {

		private Long id;
		private String code;
		private String name;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withCode(String code) {
			this.code = code;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Location build() {
			return new Location(this);
		}

	}

}
