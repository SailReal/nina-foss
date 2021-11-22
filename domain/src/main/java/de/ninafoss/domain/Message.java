package de.ninafoss.domain;

import java.io.Serializable;

public class Message implements Serializable {

	private static final Long NOT_SET = Long.MIN_VALUE;
	private final Long id;
	private final String message;
	private final Location location;

	private Message(Builder builder) {
		this.id = builder.id;
		this.message = builder.message;
		this.location = builder.location;
	}

	public static Builder aMessage() {
		return new Builder();
	}

	public static Builder aCopyOf(Message message) {
		return new Builder() //
				.withId(message.getId()) //
				.withLocation(message.getLocation()) //
				.withMessage(message.getMessage());
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		return internalEquals((Message) obj);
	}

	private boolean internalEquals(Message obj) {
		return id != null && id.equals(obj.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	public static class Builder {

		private Long id = NOT_SET;
		private String message;
		private Location location;

		private Builder() {
		}

		public Builder thatIsNew() {
			this.id = null;
			return this;
		}

		public Builder withId(Long id) {
			if (id < 1) {
				throw new IllegalArgumentException("id must not be smaller one");
			}
			this.id = id;
			return this;
		}

		public Builder withMessage(String name) {
			this.message = name;
			return this;
		}

		public Builder withLocation(Location location) {
			this.location = location;
			return this;
		}

		public Message build() {
			validate();
			return new Message(this);
		}

		private void validate() {
			if (NOT_SET.equals(id)) {
				throw new IllegalStateException("id must be set");
			}
			if (message == null) {
				throw new IllegalStateException("name must be set");
			}
		}
	}
}
