package com.myhotel.beans.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="Hotel")
@Table(name="\"HOTEL\"")
public class HotelEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonBackReference
    private HotelImage image;
    
    @Size(max = 50)
    @Column(length = 50, name="\"address\"")
    @NotNull
    private String address;

    @Size(max = 50)
    @Column(length = 50, name="\"town\"")
    @NotNull
    private String town;

    @Column(name="\"postalCode\"")
    @Digits(integer = 10, fraction = 0)
    @NotNull
    private Integer postalCode;

    @Size(max = 50)
    @Column(length = 50, name="\"country\"")
    @NotNull
    private String country;

    @Size(max = 50)
    @Column(length = 50, name="\"name\"")
    @NotNull
    private String name;

    @Size(max = 100)
    @Column(length = 100, name="\"description\"")
    @NotNull
    private String description;

    @Size(max = 50)
    @Column(length = 50, name="\"webSite\"")
    private String webSite;

    @Size(max = 50)
    @Column(length = 50, name="\"payPal\"")
    private String payPal;

    @Column(name="\"stars\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer stars;

    @Column(name="\"babyBed\"")
    private Boolean babyBed;

    public void setHoteliers(List<HotelierEntity> hoteliers) {
        this.hoteliers = hoteliers;
    }

    public List<HotelierEntity> getHoteliers() {
        return hoteliers;
    }

    @ManyToMany(mappedBy="hotels", fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonBackReference
    private List<HotelierEntity> hoteliers;

    public HotelImage getImage() {
        return image;
    }

    public void setImage(HotelImage image) {
        this.image = image;
    }
    
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return this.town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Integer getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getPayPal() {
        return this.payPal;
    }

    public void setPayPal(String payPal) {
        this.payPal = payPal;
    }

    public Integer getStars() {
        return this.stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Boolean getBabyBed() {
        return this.babyBed;
    }

    public void setBabyBed(Boolean babyBed) {
        this.babyBed = babyBed;
    }

}
