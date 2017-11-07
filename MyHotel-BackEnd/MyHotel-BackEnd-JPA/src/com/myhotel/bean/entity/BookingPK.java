package com.myhotel.bean.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the booking database table.
 * 
 */
@Embeddable
public class BookingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ROOM_ID", insertable=false, updatable=false)
	private int roomId;

	@Column(name="USER_ID", insertable=false, updatable=false)
	private int userId;

	public BookingPK() {
	}
	public int getRoomId() {
		return this.roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BookingPK)) {
			return false;
		}
		BookingPK castOther = (BookingPK)other;
		return 
			(this.roomId == castOther.roomId)
			&& (this.userId == castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roomId;
		hash = hash * prime + this.userId;
		
		return hash;
	}
}