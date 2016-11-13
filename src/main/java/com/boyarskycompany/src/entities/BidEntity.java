package com.boyarskycompany.src.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "bid", schema = "sql7142192")
@IdClass(BidEntityPK.class)
public class BidEntity {
    private long idBid;
    private Double prizeOfTransportation;
    private Timestamp applyDate;
    private Date appliedPeriod;
    private String uploadingStation;
    private String unloadingStation;
    private long idAgreement;
    private AgreementEntity agreement;
    private Collection<BidtranspmapEntity> bidtranspmaps;
    private Collection<CargoEntity> cargos;
    private Collection<TotalbillEntity> totalbills;

    @Basic
    @Column(name = "PrizeOfTransportation", nullable = true, precision = 0)
    public Double getPrizeOfTransportation() {
        return prizeOfTransportation;
    }

    public void setPrizeOfTransportation(Double prizeOfTransportation) {
        this.prizeOfTransportation = prizeOfTransportation;
    }

    @Id
    @Column(name = "IdBid", nullable = false)
    public Long getIdBid() {
        return idBid;
    }

    public void setIdBid(Long idBid) {
        this.idBid = idBid;
    }

    @Basic
    @Column(name = "ApplyDate", nullable = true)
    public Timestamp getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Timestamp applyDate) {
        this.applyDate = applyDate;
    }

    @Basic
    @Column(name = "AppliedPeriod", nullable = true)
    public Date getAppliedPeriod() {
        return appliedPeriod;
    }

    public void setAppliedPeriod(Date appliedPeriod) {
        this.appliedPeriod = appliedPeriod;
    }

    @Basic
    @Column(name = "UploadingStation", nullable = true, length = 20)
    public String getUploadingStation() {
        return uploadingStation;
    }

    public void setUploadingStation(String uploadingStation) {
        this.uploadingStation = uploadingStation;
    }

    @Basic
    @Column(name = "UnloadingStation", nullable = true, length = 25)
    public String getUnloadingStation() {
        return unloadingStation;
    }

    public void setUnloadingStation(String unloadingStation) {
        this.unloadingStation = unloadingStation;
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

        BidEntity bidEntity = (BidEntity) o;

        if (idBid != bidEntity.idBid) return false;
        if (idAgreement != bidEntity.idAgreement) return false;
        if (prizeOfTransportation != null ? !prizeOfTransportation.equals(bidEntity.prizeOfTransportation) : bidEntity.prizeOfTransportation != null)
            return false;
        if (applyDate != null ? !applyDate.equals(bidEntity.applyDate) : bidEntity.applyDate != null) return false;
        if (appliedPeriod != null ? !appliedPeriod.equals(bidEntity.appliedPeriod) : bidEntity.appliedPeriod != null)
            return false;
        if (uploadingStation != null ? !uploadingStation.equals(bidEntity.uploadingStation) : bidEntity.uploadingStation != null)
            return false;
        if (unloadingStation != null ? !unloadingStation.equals(bidEntity.unloadingStation) : bidEntity.unloadingStation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prizeOfTransportation != null ? prizeOfTransportation.hashCode() : 0;
        result = 31 * result + (int) (idBid ^ (idBid >>> 32));
        result = 31 * result + (applyDate != null ? applyDate.hashCode() : 0);
        result = 31 * result + (appliedPeriod != null ? appliedPeriod.hashCode() : 0);
        result = 31 * result + (uploadingStation != null ? uploadingStation.hashCode() : 0);
        result = 31 * result + (unloadingStation != null ? unloadingStation.hashCode() : 0);
        result = 31 * result + (int) (idAgreement ^ (idAgreement >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "IdAgreement", referencedColumnName = "IdAgreement", nullable = false, insertable = false, updatable = false)
    public AgreementEntity getAgreement() {
        return agreement;
    }

    public void setAgreement(AgreementEntity agreementByIdAgreement) {
        this.agreement = agreementByIdAgreement;
    }

    @OneToMany(mappedBy = "bid")
    public Collection<BidtranspmapEntity> getBidtranspmaps() {
        return bidtranspmaps;
    }

    public void setBidtranspmaps(Collection<BidtranspmapEntity> bidtranspmaps) {
        this.bidtranspmaps = bidtranspmaps;
    }

    @OneToMany(mappedBy = "bid")
    public Collection<CargoEntity> getCargos() {
        return cargos;
    }

    public void setCargos(Collection<CargoEntity> cargos) {
        this.cargos = cargos;
    }

    @OneToMany(mappedBy = "bid")
    public Collection<TotalbillEntity> getTotalbills() {
        return totalbills;
    }

    public void setTotalbills(Collection<TotalbillEntity> totalbills) {
        this.totalbills = totalbills;
    }
}
