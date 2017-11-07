package com.myhotel.bean.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the proposition database table.
 * 
 */
@Entity
@NamedQuery(name="Proposition.findAll", query="SELECT p FROM Proposition p")
public class Proposition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int adultprice;

	private int childrenprice;

	//bi-directional many-to-many association to Room
	@ManyToMany(mappedBy="propositions")
	private List<Room> rooms;

	//bi-directional many-to-one association to Record
	@OneToMany(mappedBy="proposition")
	private List<Record> records;

	public Proposition() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdultprice() {
		return this.adultprice;
	}

	public void setAdultprice(int adultprice) {
		this.adultprice = adultprice;
	}

	public int getChildrenprice() {
		return this.childrenprice;
	}

	public void setChildrenprice(int childrenprice) {
		this.childrenprice = childrenprice;
	}

	public List<Room> getRooms() {
		return this.rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Record> getRecords() {
		return this.records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public Record addRecord(Record record) {
		getRecords().add(record);
		record.setProposition(this);

		return record;
	}

	public Record removeRecord(Record record) {
		getRecords().remove(record);
		record.setProposition(null);

		return record;
	}

}