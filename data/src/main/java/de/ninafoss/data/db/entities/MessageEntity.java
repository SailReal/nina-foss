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

	private String message;

	/** Used to resolve relations */
	@Generated(hash = 2040040024)
	private transient DaoSession daoSession;

	/** Used for active entity operations. */
	@Generated(hash = 499759967)
	private transient MessageEntityDao myDao;

	@Generated(hash = 864867035)
	public MessageEntity(Long id, Long locationId, String message) {
		this.id = id;
		this.locationId = locationId;
		this.message = message;
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

	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 83651317)
	public void __setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
		myDao = daoSession != null ? daoSession.getMessageEntityDao() : null;
	}
}
