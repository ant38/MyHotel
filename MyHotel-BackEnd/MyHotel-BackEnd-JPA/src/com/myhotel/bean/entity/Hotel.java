package com.myhotel.bean.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the hotel database table.
 * 
 */
@Entity
@NamedQuery(name="Hotel.findAll", query="SELECT h FROM Hotel h")
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String address;

	private byte babybed;

	private String city;

	private String country;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date highseason;

	private int maxcash;

	private String name;

	private int nbstars;

	private String paypal;

	private int postalcode;

	//bi-directional many-to-one association to Specificity
	@ManyToOne
	private Specificity specificity;

	//bi-directional many-to-one association to Room
	@OneToMany(mappedBy="hotel")
	private List<Room> rooms;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="hotel")
	private List<User> users1;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="hotels")
	private List<User> users2;

	public Hotel() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte getBabybed() {
		return this.babybed;
	}

	public void setBabybed(byte babybed) {
		this.babybed = babybed;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getHighseason() {
		return this.highseason;
	}

	public void setHighseason(Date highseason) {
		this.highseason = highseason;
	}

	public int getMaxcash() {
		return this.maxcash;
	}

	public void setMaxcash(int maxcash) {
		this.maxcash = maxcash;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNbstars() {
		return this.nbstars;
	}

	public void setNbstars(int nbstars) {
		this.nbstars = nbstars;
	}

	public String getPaypal() {
		return this.paypal;
	}

	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}

	public int getPostalcode() {
		return this.postalcode;
	}

	public void setPostalcode(int postalcode) {
		this.postalcode = postalcode;
	}

	public Specificity getSpecificity() {
		return this.specificity;
	}

	public void setSpecificity(Specificity specificity) {
		this.specificity = specificity;
	}

	public List<Room> getRooms() {
		return this.rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Room addRoom(Room room) {
		getRooms().add(room);
		room.setHotel(this);

		return room;
	}

	public Room removeRoom(Room room) {
		getRooms().remove(room);
		room.setHotel(null);

		return room;
	}

	public List<User> getUsers1() {
		return this.users1;
	}

	public void setUsers1(List<User> users1) {
		this.users1 = users1;
	}

	public User addUsers1(User users1) {
		getUsers1().add(users1);
		users1.setHotel(this);

		return users1;
	}

	public User removeUsers1(User users1) {
		getUsers1().remove(users1);
		users1.setHotel(null);

		return users1;
	}

	public List<User> getUsers2() {
		return this.users2;
	}

	public void setUsers2(List<User> users2) {
		this.users2 = users2;
	}

}