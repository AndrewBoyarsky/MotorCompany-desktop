package com.boyarskycompany.src.entities;

import javax.persistence.*;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "cargo", schema = "sql7142192")
@IdClass(CargoEntityPK.class)
public class CargoEntity {
    private long idCargo;
    private String denomination;
    private String unit;
    private Integer unitsNumber;
    private Double weightGross;
    private String boxingType;
    private Double vatExcluded;
    private Double vatIncluded;
    private String dimensions;
    private Integer distance;
    private Long idWaybill;
    private Long idDriver;
    private long idBid;
    private long idAgreement;
    private WaybillEntity waybill;
    private BidEntity bid;

    @Id
    @Column(name = "IdCargo", nullable = false)
    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    @Basic
    @Column(name = "Denomination", nullable = true, length = 20)
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Basic
    @Column(name = "Unit", nullable = true, length = 20)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "UnitsNumber", nullable = true)
    public Integer getUnitsNumber() {
        return unitsNumber;
    }

    public void setUnitsNumber(Integer unitsNumber) {
        this.unitsNumber = unitsNumber;
    }

    @Basic
    @Column(name = "WeightGross", nullable = true, precision = 0)
    public Double getWeightGross() {
        return weightGross;
    }

    public void setWeightGross(Double weightGross) {
        this.weightGross = weightGross;
    }

    @Basic
    @Column(name = "BoxingType", nullable = true, length = 20)
    public String getBoxingType() {
        return boxingType;
    }

    public void setBoxingType(String boxingType) {
        this.boxingType = boxingType;
    }

    @Basic
    @Column(name = "VatExcluded", nullable = true, precision = 0)
    public Double getVatExcluded() {
        return vatExcluded;
    }

    public void setVatExcluded(Double vatExcluded) {
        this.vatExcluded = vatExcluded;
    }

    @Basic
    @Column(name = "VatIncluded", nullable = true, precision = 0)
    public Double getVatIncluded() {
        return vatIncluded;
    }

    public void setVatIncluded(Double vatIncluded) {
        this.vatIncluded = vatIncluded;
    }

    @Basic
    @Column(name = "IdWaybill", nullable = true)
    public Long getIdWaybill() {
        return idWaybill;
    }

    public void setIdWaybill(Long idWaybill) {
        this.idWaybill = idWaybill;
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
    @Column(name = "IdDriver", nullable = true)
    public Long getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(Long numberDriverLicense) {
        this.idDriver = numberDriverLicense;
    }

    @Id
    @Column(name = "IdAgreement", nullable = false)
    public Long getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(Long idAgreement) {
        this.idAgreement = idAgreement;
    }

    @Basic
    @Column(name = "Dimensions", nullable = true, length = 20)
    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    @Basic
    @Column(name = "Distance", nullable = true)
    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoEntity that = (CargoEntity) o;

        if (idCargo != that.idCargo) return false;
        if (idBid != that.idBid) return false;
        if (idAgreement != that.idAgreement) return false;
        if (denomination != null ? !denomination.equals(that.denomination) : that.denomination != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (unitsNumber != null ? !unitsNumber.equals(that.unitsNumber) : that.unitsNumber != null) return false;
        if (weightGross != null ? !weightGross.equals(that.weightGross) : that.weightGross != null) return false;
        if (boxingType != null ? !boxingType.equals(that.boxingType) : that.boxingType != null) return false;
        if (vatExcluded != null ? !vatExcluded.equals(that.vatExcluded) : that.vatExcluded != null) return false;
        if (vatIncluded != null ? !vatIncluded.equals(that.vatIncluded) : that.vatIncluded != null) return false;
        if (idWaybill != null ? !idWaybill.equals(that.idWaybill) : that.idWaybill != null) return false;
        if (idDriver != null ? !idDriver.equals(that.idDriver) : that.idDriver != null)
            return false;
        if (dimensions != null ? !dimensions.equals(that.dimensions) : that.dimensions != null) return false;
        if (distance != null ? !distance.equals(that.distance) : that.distance != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idCargo ^ (idCargo >>> 32));
        result = 31 * result + (denomination != null ? denomination.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (unitsNumber != null ? unitsNumber.hashCode() : 0);
        result = 31 * result + (weightGross != null ? weightGross.hashCode() : 0);
        result = 31 * result + (boxingType != null ? boxingType.hashCode() : 0);
        result = 31 * result + (vatExcluded != null ? vatExcluded.hashCode() : 0);
        result = 31 * result + (vatIncluded != null ? vatIncluded.hashCode() : 0);
        result = 31 * result + (idWaybill != null ? idWaybill.hashCode() : 0);
        result = 31 * result + (int) (idBid ^ (idBid >>> 32));
        result = 31 * result + (idDriver != null ? idDriver.hashCode() : 0);
        result = 31 * result + (int) (idAgreement ^ (idAgreement >>> 32));
        result = 31 * result + (dimensions != null ? dimensions.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "IdWaybill", referencedColumnName = "IdWaybill", insertable = false, updatable = false),
            @JoinColumn(name = "IdDriver", referencedColumnName = "IdDriver", insertable = false, updatable = false)})
    public WaybillEntity getWaybill() {
        return waybill;
    }

    public void setWaybill(WaybillEntity waybill) {
        this.waybill = waybill;
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
}
