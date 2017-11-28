package com.myhotel.beans.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity(name="Save")
@Table(name="\"SAVE\"")
public class SaveEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="\"date\"")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @ManyToOne(optional=true)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    private ClientEntity client;

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name="SAVE_OFFERS",
              joinColumns={@JoinColumn(name="SAVE_ID", referencedColumnName="ID")},
              inverseJoinColumns={@JoinColumn(name="OFFER_ID", referencedColumnName="ID")})
    private List<OfferEntity> offers;

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ClientEntity getClient() {
        return this.client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public List<OfferEntity> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferEntity> offers) {
        this.offers = offers;
    }

}
