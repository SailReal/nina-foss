package de.ninafoss.data.db.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class LocationEntity extends DatabaseEntity {

	@Id
	private Long id;

	private String name;

	private String code;

	@Generated(hash = 224152513)
	public LocationEntity(Long id, String name, String code) {
		this.id = id;
		this.name = name;
		this.code = code;
	}

	@Generated(hash = 1723987110)
	public LocationEntity() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
