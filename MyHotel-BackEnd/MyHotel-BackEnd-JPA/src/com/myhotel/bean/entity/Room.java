package com.myhotel.bean.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the room database table.
 * 
 */
@Entity
@NamedQuery(name="Room.findAll", query="SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int adultrooms;

	private int area;

	private byte bathroom;

	private int childrenrooms;

	private int highseasonprice;

	private byte kitchen;

	private int lowseasonprice;

	private int places;

	private int sofa;

	private byte tv;

	private String type;

	//bi-directional many-to-one association to Booking
	@OneToMany(mappedBy="room")
	private List<Booking> bookings;

	//bi-directional many-to-many association to Proposition
	@ManyToMany
	@JoinTable(
		name="propositionroom"
		, joinColumns={
			@JoinColumn(name="ROOM_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="PROPOSITION_ID")
			}
		)
	private List<Proposition> propositions;

	//bi-directional many-to-one association to Hotel
	@ManyToOne
	private Hotel hotel;

	public Room() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdultrooms() {
		return this.adultrooms;
	}

	public void setAdultrooms(int adultrooms) {
		this.adultrooms = adultrooms;
	}

	public int getArea() {
		return this.area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public byte getBathroom() {
		return this.bathroom;
	}

	public void setBathroom(byte bathroom) {
		this.bathroom = bathroom;
	}

	public int getChildrenrooms() {
		return this.childrenrooms;
	}

	public void setChildrenrooms(int childrenrooms) {
		this.childrenrooms = childrenrooms;
	}

	public int getHighseasonprice() {
		return this.highseasonprice;
	}

	public void setHighseasonprice(int highseasonprice) {
		this.highseasonprice = highseasonprice;
	}

	public byte getKitchen() {
		return this.kitchen;
	}

	public void setKitchen(byte kitchen) {
		this.kitchen = kitchen;
	}

	public int getLowseasonprice() {
		return this.lowseasonprice;
	}

	public void setLowseasonprice(int lowseasonprice) {
		this.lowseasonprice = lowseasonprice;
	}

	public int getPlaces() {
		return this.places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	public int getSofa() {
		return this.sofa;
	}

	public void setSofa(int sofa) {
		this.sofa = sofa;
	}

	public byte getTv() {
		return this.tv;
	}

	public void setTv(byte tv) {
		this.tv = tv;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Booking> getBookings() {
		return this.bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Booking addBooking(Booking booking) {
		getBookings().add(booking);
		booking.setRoom(this);

		return booking;
	}

	public Booking removeBooking(Booking booking) {
		getBookings().remove(booking);
		booking.setRoom(null);

		return booking;
	}

	public List<Proposition> getPropositions() {
		return this.propositions;
	}

	public void setPropositions(List<Proposition> propositions) {
		this.propositions = propositions;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}