package com.boyarskycompany.src.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "totalbill", schema = "sql7142192")
@IdClass(TotalbillEntityPK.class)
public class TotalbillEntity {
    private long idTotalbill;
    private Date date;
    private Date shipmentDate;
    private Double allWithoutVat;
    private Double allVat;
    private Double allWithVat;
    private long idBid;
    private long idAgreement;
    private BidEntity bid;
    private Collection<WaybillEntity> waybills;

    @Id
    @Column(name = "IdTotalBill", nullable = false)
    public Long getIdTotalbill() {
        return idTotalbill;
    }

    public void setIdTotalbill(Long idBill) {
        this.idTotalbill = idBill;
    }

    @Basic
    @Column(name = "Date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "ShipmentDate", nullable = true)
    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    @Basic
    @Column(name = "AllWithoutVAT", nullable = true, precision = 0)
    public Double getAllWithoutVat() {
        return allWithoutVat;
    }

    public void setAllWithoutVat(Double allWithoutVat) {
        this.allWithoutVat = allWithoutVat;
    }

    @Basic
    @Column(name = "allVat", nullable = true, precision = 0)
    public Double getAllVat() {
        return allVat;
    }

    public void setAllVat(Double allVat) {
        this.allVat = allVat;
    }

    @Basic
    @Column(name = "allWithVat", nullable = true, precision = 0)
    public Double getAllWithVat() {
        return allWithVat;
    }

    public void setAllWithVat(Double allWithVat) {
        this.allWithVat = allWithVat;
    }

    @Id
    @Column(name = "IdBid", nullable = false)
    public Long getIdBid() {
        return idBid;
    }

    public void setIdBid(Long idBid) {
        this.idBid = idBid;
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

        TotalbillEntity that = (TotalbillEntity) o;

        if (idTotalbill != that.idTotalbill) return false;
        if (idBid != that.idBid) return false;
        if (idAgreement != that.idAgreement) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (shipmentDate != null ? !shipmentDate.equals(that.shipmentDate) : that.shipmentDate != null) return false;
        if (allWithoutVat != null ? !allWithoutVat.equals(that.allWithoutVat) : that.allWithoutVat != null)
            return false;
        if (allVat != null ? !allVat.equals(that.allVat) : that.allVat != null) return false;
        if (allWithVat != null ? !allWithVat.equals(that.allWithVat) : that.allWithVat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTotalbill ^ (idTotalbill >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (shipmentDate != null ? shipmentDate.hashCode() : 0);
        result = 31 * result + (allWithoutVat != null ? allWithoutVat.hashCode() : 0);
        result = 31 * result + (allVat != null ? allVat.hashCode() : 0);
        result = 31 * result + (allWithVat != null ? allWithVat.hashCode() : 0);
        result = 31 * result + (int) (idBid ^ (idBid >>> 32));
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

    @OneToMany(mappedBy = "totalbill")
    public Collection<WaybillEntity> getWaybills() {
        return waybills;
    }

    public void setWaybills(Collection<WaybillEntity> waybills) {
        this.waybills = waybills;
    }
}
