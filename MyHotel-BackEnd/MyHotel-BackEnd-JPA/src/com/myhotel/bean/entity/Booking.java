package com.myhotel.bean.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BookingPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datein;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateout;

	private byte payment;

	//bi-directional many-to-one association to Room
	@ManyToOne
	private Room room;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Booking() {
	}

	public BookingPK getId() {
		return this.id;
	}

	public void setId(BookingPK id) {
		this.id = id;
	}

	public Date getDatein() {
		return this.datein;
	}

	public void setDatein(Date datein) {
		this.datein = datein;
	}

	public Date getDateout() {
		return this.dateout;
	}

	public void setDateout(Date dateout) {
		this.dateout = dateout;
	}

	public byte getPayment() {
		return this.payment;
	}

	public void setPayment(byte payment) {
		this.payment = payment;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}