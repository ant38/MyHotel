package com.myhotel.bean.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the record database table.
 * 
 */
@Embeddable
public class RecordPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PROPOSITION_ID", insertable=false, updatable=false)
	private int propositionId;

	@Column(name="USER_ID", insertable=false, updatable=false)
	private int userId;

	public RecordPK() {
	}
	public int getPropositionId() {
		return this.propositionId;
	}
	public void setPropositionId(int propositionId) {
		this.propositionId = propositionId;
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
		if (!(other instanceof RecordPK)) {
			return false;
		}
		RecordPK castOther = (RecordPK)other;
		return 
			(this.propositionId == castOther.propositionId)
			&& (this.userId == castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.propositionId;
		hash = hash * prime + this.userId;
		
		return hash;
	}
}