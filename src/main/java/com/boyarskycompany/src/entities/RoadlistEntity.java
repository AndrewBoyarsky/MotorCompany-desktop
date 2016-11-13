package com.boyarskycompany.src.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "roadlist", schema = "sql7142192")
@IdClass(RoadlistEntityPK.class)
public class RoadlistEntity {
    private long idRoadlist;
    private Double totalHours;
    private String dispatcher;
    private String mechanic;
    private Timestamp arrivalActualTime;
    private Double zeroMileage;
    private Double speedometrIndicate;
    private String fuelMark;
    private Double fuelIssued;
    private Double arrivalFuelBalance;
    private Double departureFuelBalance;
    private Double totalTons;
    private Integer totalTrips;
    private long idVariabledayplan;
    private long idTranspmap;
    private long idDriver;
    private Collection<DriverjobEntity> driverjobs;
    private Collection<ProvidedserviceEntity> providedservices;
    private VariabledayplanEntity variabledayplan;
    private DriverEntity driver;

    @Id
    @Column(name = "IdRoadList", nullable = false)
    public Long getIdRoadlist() {
        return idRoadlist;
    }

    public void setIdRoadlist(Long idRoadList) {
        this.idRoadlist = idRoadList;
    }

    @Basic
    @Column(name = "TotalHours", nullable = true, precision = 0)
    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    @Basic
    @Column(name = "Dispatcher", nullable = true, length = 20)
    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Basic
    @Column(name = "Mechanic", nullable = true, length = 20)
    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    @Basic
    @Column(name = "ArrivalActualTime", nullable = true)
    public Timestamp getArrivalActualTime() {
        return arrivalActualTime;
    }

    public void setArrivalActualTime(Timestamp arrivalActualTime) {
        this.arrivalActualTime = arrivalActualTime;
    }

    @Basic
    @Column(name = "ZeroMileage", nullable = true, precision = 0)
    public Double getZeroMileage() {
        return zeroMileage;
    }

    public void setZeroMileage(Double zeroMileage) {
        this.zeroMileage = zeroMileage;
    }

    @Basic
    @Column(name = "SpeedometrIndicate", nullable = true, precision = 0)
    public Double getSpeedometrIndicate() {
        return speedometrIndicate;
    }

    public void setSpeedometrIndicate(Double speedometrIndicate) {
        this.speedometrIndicate = speedometrIndicate;
    }

    @Basic
    @Column(name = "FuelMark", nullable = true, length = 20)
    public String getFuelMark() {
        return fuelMark;
    }

    public void setFuelMark(String fuelMark) {
        this.fuelMark = fuelMark;
    }

    @Basic
    @Column(name = "FuelIssued", nullable = true, precision = 0)
    public Double getFuelIssued() {
        return fuelIssued;
    }

    public void setFuelIssued(Double fuelIssued) {
        this.fuelIssued = fuelIssued;
    }

    @Basic
    @Column(name = "ArrivalFuelBalance", nullable = true, precision = 0)
    public Double getArrivalFuelBalance() {
        return arrivalFuelBalance;
    }

    public void setArrivalFuelBalance(Double arrivalFuelBalance) {
        this.arrivalFuelBalance = arrivalFuelBalance;
    }

    @Basic
    @Column(name = "DepartureFuelBalance", nullable = true, precision = 0)
    public Double getDepartureFuelBalance() {
        return departureFuelBalance;
    }

    public void setDepartureFuelBalance(Double departureFuelBalance) {
        this.departureFuelBalance = departureFuelBalance;
    }

    @Basic
    @Column(name = "TotalTons", nullable = true, precision = 0)
    public Double getTotalTons() {
        return totalTons;
    }

    public void setTotalTons(Double totalTons) {
        this.totalTons = totalTons;
    }

    @Basic
    @Column(name = "TotalTrips", nullable = true)
    public Integer getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(Integer totalTrips) {
        this.totalTrips = totalTrips;
    }

    @Id
    @Column(name = "IdVariableDayPlan", nullable = false)
    public Long getIdVariabledayplan() {
        return idVariabledayplan;
    }

    public void setIdVariabledayplan(Long idVariableDayPlan) {
        this.idVariabledayplan = idVariableDayPlan;
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
    @Column(name = "idDriver", nullable = false)
    public Long getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(Long idDriver) {
        this.idDriver = idDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadlistEntity that = (RoadlistEntity) o;

        if (idRoadlist != that.idRoadlist) return false;
        if (idVariabledayplan != that.idVariabledayplan) return false;
        if (idTranspmap != that.idTranspmap) return false;
        if (idDriver != that.idDriver) return false;
        if (totalHours != null ? !totalHours.equals(that.totalHours) : that.totalHours != null) return false;
        if (dispatcher != null ? !dispatcher.equals(that.dispatcher) : that.dispatcher != null) return false;
        if (mechanic != null ? !mechanic.equals(that.mechanic) : that.mechanic != null) return false;
        if (arrivalActualTime != null ? !arrivalActualTime.equals(that.arrivalActualTime) : that.arrivalActualTime != null)
            return false;
        if (zeroMileage != null ? !zeroMileage.equals(that.zeroMileage) : that.zeroMileage != null) return false;
        if (speedometrIndicate != null ? !speedometrIndicate.equals(that.speedometrIndicate) : that.speedometrIndicate != null)
            return false;
        if (fuelMark != null ? !fuelMark.equals(that.fuelMark) : that.fuelMark != null) return false;
        if (fuelIssued != null ? !fuelIssued.equals(that.fuelIssued) : that.fuelIssued != null) return false;
        if (arrivalFuelBalance != null ? !arrivalFuelBalance.equals(that.arrivalFuelBalance) : that.arrivalFuelBalance != null)
            return false;
        if (departureFuelBalance != null ? !departureFuelBalance.equals(that.departureFuelBalance) : that.departureFuelBalance != null)
            return false;
        if (totalTons != null ? !totalTons.equals(that.totalTons) : that.totalTons != null) return false;
        if (totalTrips != null ? !totalTrips.equals(that.totalTrips) : that.totalTrips != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idRoadlist ^ (idRoadlist >>> 32));
        result = 31 * result + (totalHours != null ? totalHours.hashCode() : 0);
        result = 31 * result + (dispatcher != null ? dispatcher.hashCode() : 0);
        result = 31 * result + (mechanic != null ? mechanic.hashCode() : 0);
        result = 31 * result + (arrivalActualTime != null ? arrivalActualTime.hashCode() : 0);
        result = 31 * result + (zeroMileage != null ? zeroMileage.hashCode() : 0);
        result = 31 * result + (speedometrIndicate != null ? speedometrIndicate.hashCode() : 0);
        result = 31 * result + (fuelMark != null ? fuelMark.hashCode() : 0);
        result = 31 * result + (fuelIssued != null ? fuelIssued.hashCode() : 0);
        result = 31 * result + (arrivalFuelBalance != null ? arrivalFuelBalance.hashCode() : 0);
        result = 31 * result + (departureFuelBalance != null ? departureFuelBalance.hashCode() : 0);
        result = 31 * result + (totalTons != null ? totalTons.hashCode() : 0);
        result = 31 * result + (totalTrips != null ? totalTrips.hashCode() : 0);
        result = 31 * result + (int) (idVariabledayplan ^ (idVariabledayplan >>> 32));
        result = 31 * result + (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
        return result;
    }

    @OneToMany(mappedBy = "roadlist")
    public Collection<DriverjobEntity> getDriverjobs() {
        return driverjobs;
    }

    public void setDriverjobs(Collection<DriverjobEntity> driverjobs) {
        this.driverjobs = driverjobs;
    }

    @OneToMany(mappedBy = "roadlist")
    public Collection<ProvidedserviceEntity> getProvidedservices() {
        return providedservices;
    }

    public void setProvidedservices(Collection<ProvidedserviceEntity> providedservices) {
        this.providedservices = providedservices;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "IdVariableDayPlan", referencedColumnName = "IdVariableDayPlan", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "IdTranspMap", referencedColumnName = "IdTranspMap", nullable = false, insertable = false, updatable = false)})
    public VariabledayplanEntity getVariabledayplan() {
        return variabledayplan;
    }

    public void setVariabledayplan(VariabledayplanEntity variabledayplan) {
        this.variabledayplan = variabledayplan;
    }

    @ManyToOne
    @JoinColumn(name = "idDriver", referencedColumnName = "idDriver", nullable = false, insertable = false, updatable = false)
    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity driversByNumberDriverLicense) {
        this.driver = driversByNumberDriverLicense;
    }
}
