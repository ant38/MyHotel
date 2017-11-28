package com.myhotel.beans.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="UserComment")
@Table(name="\"USERCOMMENT\"")
public class UserCommentEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private UserCommentImage image;
    
    @Column(name="\"rating\"")
    @Digits(integer = 4, fraction = 0)
    private Integer rating;

    @Size(max = 1000)
    @Column(length = 1000, name="\"comment\"")
    @NotNull
    private String comment;

    @ManyToOne(optional=true)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    private ClientEntity client;

    @ManyToOne(optional=true)
    @JoinColumn(name = "HOTEL_ID", referencedColumnName = "ID")
    private HotelEntity hotel;

    public UserCommentImage getImage() {
        return image;
    }

    public void setImage(UserCommentImage image) {
        this.image = image;
    }
    
    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ClientEntity getClient() {
        return this.client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public HotelEntity getHotel() {
        return this.hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

}
