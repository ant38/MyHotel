package com.myhotel.beans.domain;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="Client")
@Table(name="\"CLIENT\"")
public class ClientEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 50)
    @Column(length = 50, name="\"username\"")
    @NotNull
    private String username;

    @Size(max = 50)
    @Column(length = 50, name="\"password\"")
    @NotNull
    private String password;

    @Size(max = 50)
    @Column(length = 50, name="\"prenom\"")
    @NotNull
    private String prenom;

    @Size(max = 50)
    @Column(length = 50, name="\"nom\"")
    @NotNull
    private String nom;

    @Column(name="\"dateNaissance\"")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateNaissance;

    @Size(max = 50)
    @Column(length = 50, name="\"email\"")
    @NotNull
    private String email;

    @Column(name="\"phone\"")
    @Digits(integer = 4, fraction = 0)
    private Integer phone;

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    @ManyToMany(mappedBy="clients", fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonBackReference
    private List<BookingEntity> bookings;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return this.phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

}
