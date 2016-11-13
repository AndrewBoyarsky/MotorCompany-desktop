package com.boyarskycompany.src.entities;

import javax.persistence.*;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "bidtranspmap", schema = "sql7142192")
@IdClass(BidtranspmapEntityPK.class)
public class BidtranspmapEntity {
    private long idBid;
    private long idTranspmap;
    private long idAgreement;
    private BidEntity bid;
    private TranspmapEntity transpmap;

    @Id
    @Column(name = "IdBid", nullable = false)
    public Long getIdBid() {
        return idBid;
    }

    public void setIdBid(Long idBid) {
        this.idBid = idBid;
    }

    @Id
    @Column(name = "IdTranspMap", nullable = false)
    public Long getIdTranspmap() {
        return idTranspmap;
    }

    public void setIdTranspmap(Long idTranspMap) {
        this.idTranspmap = idTranspMap;
    }

    @Id
    @Column(name = "IdAgreement", nullable = false)
    public Long getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(Long idAgreement) {
        this.idAgreement = idAgreement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BidtranspmapEntity that = (BidtranspmapEntity) o;

        if (idBid != that.idBid) return false;
        if (idTranspmap != that.idTranspmap) return false;
        if (idAgreement != that.idAgreement) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idBid ^ (idBid >>> 32));
        result = 31 * result + (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (int) (idAgreement ^ (idAgreement >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "IdBid", referencedColumnName = "IdBid", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "IdAgreement", referencedColumnName = "IdAgreement", nullable = false, insertable = false, updatable = false)})
    public BidEntity getBid() {
        return bid;
    }

    public void setBid(BidEntity bid) {
        this.bid = bid;
    }

    @ManyToOne
    @JoinColumn(name = "IdTranspMap", referencedColumnName = "IdTranspMap", nullable = false, insertable = false, updatable = false)
    public TranspmapEntity getTranspmap() {
        return transpmap;
    }

    public void setTranspmap(TranspmapEntity transpmapByIdTranspMap) {
        this.transpmap = transpmapByIdTranspMap;
    }
}
