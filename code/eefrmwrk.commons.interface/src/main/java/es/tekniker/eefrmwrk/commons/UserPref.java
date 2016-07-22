package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

public class UserPref implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6566190185425031655L;
	
	private long userPrefId;
	private long userId;
	private String code;
	private String type;
	private String value;

	
	public UserPref() {
		super();
		this.setUserId(0);
		this.setUserPrefId(0);
		this.setCode("");
		this.setType("");
		this.setValue("");
	}
	public UserPref(long userPrefId, long userId, String code, String type,
			String value) {
		super();
		this.userPrefId = userPrefId;
		this.userId = userId;
		this.code = code;
		this.type = type;
		this.value = value;
	}

	public long getUserPrefId() {
		return userPrefId;
	}
	public void setUserPrefId(long userPrefId) {
		this.userPrefId = userPrefId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPref other = (UserPref) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}


}
