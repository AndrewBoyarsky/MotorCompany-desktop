package com.boyarskycompany.src.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "transport", schema = "sql7142192")
public class TransportEntity {
    private long idTransport;
    private String type;
    private String mark;
    private String model;
    private Double carrying;
    private Double fuelCost;
    private Integer issueYear;
    private Date comissioningDate;
    private Integer mileage;
    private String inventoryNumber;
    private Date dateLastMajorOverhaul;
    private Integer mileageSinceLastMajorOverhaul;
    private Double cost;
    private Double residualValue;
    private Double demolition;
    private Boolean isUsing;
    private Collection<TranspmaptransportEntity> transpmaptransports;

    @Basic
    @Column(name = "IsUsing", nullable = false)
    public Boolean getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(Boolean isUsing) {
        this.isUsing = isUsing;
    }

    @Basic
    @Column(name = "Type", nullable = true, length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "Mark", nullable = true, length = 18)
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Basic
    @Column(name = "Model", nullable = true, length = 18)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "Carrying", nullable = true, precision = 0)
    public Double getCarrying() {
        return carrying;
    }

    public void setCarrying(Double carrying) {
        this.carrying = carrying;
    }

    @Basic
    @Column(name = "FuelCost", nullable = true, precision = 0)
    public Double getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(Double fuelCost) {
        this.fuelCost = fuelCost;
    }

    @Basic
    @Column(name = "IssueYear", nullable = true)
    public Integer getIssueYear() {
        return issueYear;
    }

    public void setIssueYear(Integer issueYear) {
        this.issueYear = issueYear;
    }

    @Basic
    @Column(name = "ComissioningDate", nullable = true)
    public Date getComissioningDate() {
        return comissioningDate;
    }

    public void setComissioningDate(Date comissioningDate) {
        this.comissioningDate = comissioningDate;
    }

    @Basic
    @Column(name = "Mileage", nullable = true)
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Id
    @Column(name = "IdTransport", nullable = false)
    public Long getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(Long registrationCertificateNumber) {
        this.idTransport = registrationCertificateNumber;
    }

    @Basic
    @Column(name = "InventoryNumber", nullable = true, length = 18)
    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    @Basic
    @Column(name = "DateLastMajorOverhaul", nullable = true)
    public Date getDateLastMajorOverhaul() {
        return dateLastMajorOverhaul;
    }

    public void setDateLastMajorOverhaul(Date dateLastMajorOverhaul) {
        this.dateLastMajorOverhaul = dateLastMajorOverhaul;
    }

    @Basic
    @Column(name = "MileageSinceLastMajorOverhaul", nullable = true)
    public Integer getMileageSinceLastMajorOverhaul() {
        return mileageSinceLastMajorOverhaul;
    }

    public void setMileageSinceLastMajorOverhaul(Integer mileageSinceLastMajorOverhaul) {
        this.mileageSinceLastMajorOverhaul = mileageSinceLastMajorOverhaul;
    }

    @Basic
    @Column(name = "Cost", nullable = true, precision = 0)
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "ResidualValue", nullable = true, precision = 0)
    public Double getResidualValue() {
        return residualValue;
    }

    public void setResidualValue(Double residualValue) {
        this.residualValue = residualValue;
    }

    @Basic
    @Column(name = "Demolition", nullable = true, precision = 0)
    public Double getDemolition() {
        return demolition;
    }

    public void setDemolition(Double demolition) {
        this.demolition = demolition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransportEntity that = (TransportEntity) o;

        if (idTransport != that.idTransport) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (carrying != null ? !carrying.equals(that.carrying) : that.carrying != null) return false;
        if (fuelCost != null ? !fuelCost.equals(that.fuelCost) : that.fuelCost != null) return false;
        if (issueYear != null ? !issueYear.equals(that.issueYear) : that.issueYear != null) return false;
        if (comissioningDate != null ? !comissioningDate.equals(that.comissioningDate) : that.comissioningDate != null)
            return false;
        if (mileage != null ? !mileage.equals(that.mileage) : that.mileage != null) return false;
        if (inventoryNumber != null ? !inventoryNumber.equals(that.inventoryNumber) : that.inventoryNumber != null)
            return false;
        if (dateLastMajorOverhaul != null ? !dateLastMajorOverhaul.equals(that.dateLastMajorOverhaul) : that.dateLastMajorOverhaul != null)
            return false;
        if (mileageSinceLastMajorOverhaul != null ? !mileageSinceLastMajorOverhaul.equals(that.mileageSinceLastMajorOverhaul) : that.mileageSinceLastMajorOverhaul != null)
            return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (residualValue != null ? !residualValue.equals(that.residualValue) : that.residualValue != null)
            return false;
        if (demolition != null ? !demolition.equals(that.demolition) : that.demolition != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (carrying != null ? carrying.hashCode() : 0);
        result = 31 * result + (fuelCost != null ? fuelCost.hashCode() : 0);
        result = 31 * result + (issueYear != null ? issueYear.hashCode() : 0);
        result = 31 * result + (comissioningDate != null ? comissioningDate.hashCode() : 0);
        result = 31 * result + (mileage != null ? mileage.hashCode() : 0);
        result = 31 * result + (int) (idTransport ^ (idTransport >>> 32));
        result = 31 * result + (inventoryNumber != null ? inventoryNumber.hashCode() : 0);
        result = 31 * result + (dateLastMajorOverhaul != null ? dateLastMajorOverhaul.hashCode() : 0);
        result = 31 * result + (mileageSinceLastMajorOverhaul != null ? mileageSinceLastMajorOverhaul.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (residualValue != null ? residualValue.hashCode() : 0);
        result = 31 * result + (demolition != null ? demolition.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "transport")
    public Collection<TranspmaptransportEntity> getTranspmaptransports() {
        return transpmaptransports;
    }

    public void setTranspmaptransports(Collection<TranspmaptransportEntity> transpmaptransportsByRegistrationCertificateNumber) {
        this.transpmaptransports = transpmaptransportsByRegistrationCertificateNumber;
    }
}
