package com.myhotel.beans.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="Offer")
@Table(name="\"OFFER\"")
public class OfferEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="\"dateStart\"")
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Column(name="\"dateEnd\"")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateEnd;

    @Column(name="\"price\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer price;

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    @ManyToMany(mappedBy="offers", fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private List<RoomEntity> rooms = new ArrayList<>();

    public void setSaves(List<SaveEntity> saves) {
        this.saves = saves;
    }

    public List<SaveEntity> getSaves() {
        return saves;
    }

    @ManyToMany(mappedBy="offers", fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonBackReference
    private List<SaveEntity> saves;

    public Date getDateStart() {
        return this.dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return this.dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
