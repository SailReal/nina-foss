package de.ninafoss.domain;

import java.io.Serializable;

public class Message implements Serializable {

	private static final Long NOT_SET = Long.MIN_VALUE;
	private final Long id;
	private final String remoteId;
	private final Integer remoteVersion;
	private final String instruction;
	private final String severity;
	private final String contact;
	private final String headline;
	private final String message;
	private final String provider;
	private final String msgType;
	private final String sentDate;
	private final Location location;

	private Message(Builder builder) {
		this.id = builder.id;
		this.message = builder.message;
		this.location = builder.location;
		this.remoteId = builder.remoteId;
		this.remoteVersion = builder.remoteVersion;
		this.instruction = builder.instruction;
		this.severity = builder.severity;
		this.contact = builder.contact;
		this.headline = builder.headline;
		this.provider = builder.provider;
		this.msgType = builder.msgType;
		this.sentDate = builder.sentDate;
	}

	public static Builder aMessage() {
		return new Builder();
	}

	public static Builder aCopyOf(Message message) {
		return new Builder() //
				.withId(message.getId()) //
				.withLocation(message.getLocation()) //
				.withMessage(message.getMessage()) //
				.withRemoveVersion(message.getRemoteVersion()) //
				.withInstruction(message.getInstruction()) //
				.withSeverity(message.getSeverity()) //
				.withContact(message.getContact()) //
				.withHeadline(message.getHeadline()) //
				.withProvider(message.getProvider()) //
				.withMessageType(message.getContact()) //
				.withSentDate(message.getSentDate());
	}

	public Long getId() {
		return id;
	}

	public String getRemoteId() {
		return remoteId;
	}

	public String getMessage() {
		return message;
	}

	public Location getLocation() {
		return location;
	}

	public String getInstruction() {
		return instruction;
	}

	public Integer getRemoteVersion() {
		return remoteVersion;
	}

	public String getSeverity() {
		return severity;
	}

	public String getContact() {
		return contact;
	}

	public String getHeadline() {
		return headline;
	}

	public String getProvider() {
		return provider;
	}

	public String getMsgType() {
		return msgType;
	}

	public String getSentDate() {
		return sentDate;
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
		private String remoteId;
		private Integer remoteVersion;
		private String instruction;
		private String severity;
		private String contact;
		private String headline;
		private String message;
		private String provider;
		private String msgType;
		private String sentDate;
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


		public Builder withRemoteId(String remoteId) {
			this.remoteId = remoteId;
			return this;
		}

		public Builder withRemoveVersion(Integer remoteVersion) {
			this.remoteVersion = remoteVersion;
			return this;
		}

		public Builder withInstruction(String instruction) {
			this.instruction = instruction;
			return this;
		}

		public Builder withSeverity(String severity) {
			this.severity = severity;
			return this;
		}

		public Builder withContact(String contact) {
			this.contact = contact;
			return this;
		}

		public Builder withHeadline(String headline) {
			this.headline = headline;
			return this;
		}

		public Builder withMessage(String name) {
			this.message = name;
			return this;
		}

		public Builder withProvider(String provider) {
			this.provider = provider;
			return this;
		}

		public Builder withMessageType(String messageType) {
			this.msgType = messageType;
			return this;
		}

		public Builder withSentDate(String sentDate) {
			this.sentDate = sentDate;
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
