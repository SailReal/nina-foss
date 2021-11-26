package de.ninafoss.data.db.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(indexes = {@Index(value = "locationId", unique = true)})
public class MessageEntity extends DatabaseEntity {

	@Id
	private Long id;

	private Long locationId;

	@ToOne(joinProperty = "locationId")
	private LocationEntity locationEntity;

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

	/** Used to resolve relations */
	@Generated(hash = 2040040024)
	private transient DaoSession daoSession;

	/** Used for active entity operations. */
	@Generated(hash = 499759967)
	private transient MessageEntityDao myDao;

	@Generated(hash = 922324642)
	public MessageEntity(Long id, Long locationId, String remoteId, Integer remoteVersion, String instruction, String severity, String contact, String headline,
			String message, String provider, String msgType, String sentDate) {
		this.id = id;
		this.locationId = locationId;
		this.remoteId = remoteId;
		this.remoteVersion = remoteVersion;
		this.instruction = instruction;
		this.severity = severity;
		this.contact = contact;
		this.headline = headline;
		this.message = message;
		this.provider = provider;
		this.msgType = msgType;
		this.sentDate = sentDate;
	}

	@Generated(hash = 1797882234)
	public MessageEntity() {
	}

	@Generated(hash = 1848288970)
	private transient Long locationEntity__resolvedKey;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLocationId() {
		return this.locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public LocationEntity getLocation() {
		return locationEntity;
	}

	public void setLocation(LocationEntity locationEntity) {
		this.locationEntity = locationEntity;
	}

	/** To-one relationship, resolved on first access. */
	@Generated(hash = 1398915076)
	public LocationEntity getLocationEntity() {
		Long __key = this.locationId;
		if (locationEntity__resolvedKey == null
				|| !locationEntity__resolvedKey.equals(__key)) {
			final DaoSession daoSession = this.daoSession;
			if (daoSession == null) {
				throw new DaoException("Entity is detached from DAO context");
			}
			LocationEntityDao targetDao = daoSession.getLocationEntityDao();
			LocationEntity locationEntityNew = targetDao.load(__key);
			synchronized (this) {
				locationEntity = locationEntityNew;
				locationEntity__resolvedKey = __key;
			}
		}
		return locationEntity;
	}

	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 55744893)
	public void setLocationEntity(LocationEntity locationEntity) {
		synchronized (this) {
			this.locationEntity = locationEntity;
			locationId = locationEntity == null ? null : locationEntity.getId();
			locationEntity__resolvedKey = locationId;
		}
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 128553479)
	public void delete() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.delete(this);
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 1942392019)
	public void refresh() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.refresh(this);
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 713229351)
	public void update() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.update(this);
	}

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}

	public Integer getRemoteVersion() {
		return this.remoteVersion;
	}

	public void setRemoteVersion(Integer remoteVersion) {
		this.remoteVersion = remoteVersion;
	}

	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getHeadline() {
		return this.headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getSentDate() {
		return this.sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 83651317)
	public void __setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
		myDao = daoSession != null ? daoSession.getMessageEntityDao() : null;
	}
}
