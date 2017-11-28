package com.myhotel.beans.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="Room")
@Table(name="\"ROOM\"")
public class RoomEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="\"places\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer places;

    @Column(name="\"bathroom\"")
    private Boolean bathroom;

    @Column(name="\"kitchen\"")
    private Boolean kitchen;

    @Column(name="\"doubleBed\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer doubleBed;

    @Column(name="\"simpleBed\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer simpleBed;

    @Column(name="\"sofaBed\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer sofaBed;

    @Column(name="\"tv\"")
    private Boolean tv;

    @Column(name="\"size\"")
    @Digits(integer = 4, fraction = 0)
    private Integer size;

    @Column(name="\"TYPE\"")
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Column(name="\"roomNumber\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer roomNumber;

    @ManyToOne(optional=true)
    @JoinColumn(name = "HOTEL_ID", referencedColumnName = "ID")
    private HotelEntity hotel;

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name="ROOM_OFFERS",
              joinColumns={@JoinColumn(name="ROOM_ID", referencedColumnName="ID")},
              inverseJoinColumns={@JoinColumn(name="OFFER_ID", referencedColumnName="ID")})
    private List<OfferEntity> offers;

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    @ManyToMany(mappedBy="rooms", fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<BookingEntity> bookings;

    public Integer getPlaces() {
        return this.places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public Boolean getBathroom() {
        return this.bathroom;
    }

    public void setBathroom(Boolean bathroom) {
        this.bathroom = bathroom;
    }

    public Boolean getKitchen() {
        return this.kitchen;
    }

    public void setKitchen(Boolean kitchen) {
        this.kitchen = kitchen;
    }

    public Integer getDoubleBed() {
        return this.doubleBed;
    }

    public void setDoubleBed(Integer doubleBed) {
        this.doubleBed = doubleBed;
    }

    public Integer getSimpleBed() {
        return this.simpleBed;
    }

    public void setSimpleBed(Integer simpleBed) {
        this.simpleBed = simpleBed;
    }

    public Integer getSofaBed() {
        return this.sofaBed;
    }

    public void setSofaBed(Integer sofaBed) {
        this.sofaBed = sofaBed;
    }

    public Boolean getTv() {
        return this.tv;
    }

    public void setTv(Boolean tv) {
        this.tv = tv;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public Integer getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public HotelEntity getHotel() {
        return this.hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    public List<OfferEntity> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferEntity> offers) {
        this.offers = offers;
    }

}
