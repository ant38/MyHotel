package com.myhotel.bean.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the specificity database table.
 * 
 */
@Entity
@NamedQuery(name="Specificity.findAll", query="SELECT s FROM Specificity s")
public class Specificity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private byte bar;

	private byte breakfast;

	private byte calm;

	private byte freebreakfast;

	private byte gameroom;

	private byte pool;

	//bi-directional many-to-one association to Hotel
	@OneToMany(mappedBy="specificity")
	private List<Hotel> hotels;

	public Specificity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getBar() {
		return this.bar;
	}

	public void setBar(byte bar) {
		this.bar = bar;
	}

	public byte getBreakfast() {
		return this.breakfast;
	}

	public void setBreakfast(byte breakfast) {
		this.breakfast = breakfast;
	}

	public byte getCalm() {
		return this.calm;
	}

	public void setCalm(byte calm) {
		this.calm = calm;
	}

	public byte getFreebreakfast() {
		return this.freebreakfast;
	}

	public void setFreebreakfast(byte freebreakfast) {
		this.freebreakfast = freebreakfast;
	}

	public byte getGameroom() {
		return this.gameroom;
	}

	public void setGameroom(byte gameroom) {
		this.gameroom = gameroom;
	}

	public byte getPool() {
		return this.pool;
	}

	public void setPool(byte pool) {
		this.pool = pool;
	}

	public List<Hotel> getHotels() {
		return this.hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	public Hotel addHotel(Hotel hotel) {
		getHotels().add(hotel);
		hotel.setSpecificity(this);

		return hotel;
	}

	public Hotel removeHotel(Hotel hotel) {
		getHotels().remove(hotel);
		hotel.setSpecificity(null);

		return hotel;
	}

}