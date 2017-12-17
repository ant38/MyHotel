package com.myhotel.beans.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="Booking")
@Table(name="\"BOOKING\"")
public class BookingEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="\"dateIn\"")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateIn;

    @Column(name="\"dateOut\"")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateOut;

    @Column(name="\"paid\"")
    private Boolean paid;

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name="BOOKING_ROOMS",
              joinColumns={@JoinColumn(name="BOOKING_ID", referencedColumnName="ID")},
              inverseJoinColumns={@JoinColumn(name="ROOM_ID", referencedColumnName="ID")})
    @JsonManagedReference
    private List<RoomEntity> rooms = new ArrayList<>();

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name="BOOKING_CLIENTS",
              joinColumns={@JoinColumn(name="BOOKING_ID", referencedColumnName="ID")},
              inverseJoinColumns={@JoinColumn(name="CLIENT_ID", referencedColumnName="ID")})
    @JsonManagedReference
    private List<ClientEntity> clients;

    public Date getDateIn() {
        return this.dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return this.dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Boolean getPaid() {
        return this.paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    public List<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(List<ClientEntity> clients) {
        this.clients = clients;
    }

}
