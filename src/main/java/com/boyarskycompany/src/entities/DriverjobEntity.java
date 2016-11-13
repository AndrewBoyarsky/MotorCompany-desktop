package com.boyarskycompany.src.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "driverjob", schema = "sql7142192")
@IdClass(DriverjobEntityPK.class)
public class DriverjobEntity {
    private long idDriverjob;
    private Timestamp arrivalTime;
    private Timestamp departureTime;
    private Integer numberHours;
    private String whereGetCargo;
    private String whereDeliverCargo;
    private String denomination;
    private Integer numberTrips;
    private Double distance;
    private Double tonsTransported;
    private long idRoadlist;
    private long idVariabledayplan;
    private long idTranspmap;
    private long idDriver;
    private RoadlistEntity roadlist;

    @Id
    @Column(name = "IdDriverJob", nullable = false)
    public Long getIdDriverjob() {
        return idDriverjob;
    }

    public void setIdDriverjob(Long idDriverJob) {
        this.idDriverjob = idDriverJob;
    }

    @Basic
    @Column(name = "ArrivalTime", nullable = true)
    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Basic
    @Column(name = "DepartureTime", nullable = true)
    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    @Basic
    @Column(name = "NumberHours", nullable = true)
    public Integer getNumberHours() {
        return numberHours;
    }

    public void setNumberHours(Integer numberHours) {
        this.numberHours = numberHours;
    }

    @Basic
    @Column(name = "WhereGetCargo", nullable = true, length = 20)
    public String getWhereGetCargo() {
        return whereGetCargo;
    }

    public void setWhereGetCargo(String whereGetCargo) {
        this.whereGetCargo = whereGetCargo;
    }

    @Basic
    @Column(name = "WhereDeliverCargo", nullable = true, length = 20)
    public String getWhereDeliverCargo() {
        return whereDeliverCargo;
    }

    public void setWhereDeliverCargo(String whereDeliverCargo) {
        this.whereDeliverCargo = whereDeliverCargo;
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
    @Column(name = "NumberTrips", nullable = true)
    public Integer getNumberTrips() {
        return numberTrips;
    }

    public void setNumberTrips(Integer numberTrips) {
        this.numberTrips = numberTrips;
    }

    @Basic
    @Column(name = "Distance", nullable = true, precision = 0)
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "TonsTransported", nullable = true, precision = 0)
    public Double getTonsTransported() {
        return tonsTransported;
    }

    public void setTonsTransported(Double tonsTransported) {
        this.tonsTransported = tonsTransported;
    }

    @Id
    @Column(name = "IdRoadList", nullable = false)
    public Long getIdRoadlist() {
        return idRoadlist;
    }

    public void setIdRoadlist(Long idRoadList) {
        this.idRoadlist = idRoadList;
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
    @Column(name = "IdDriver", nullable = false)
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

        DriverjobEntity that = (DriverjobEntity) o;

        if (idDriverjob != that.idDriverjob) return false;
        if (idRoadlist != that.idRoadlist) return false;
        if (idVariabledayplan != that.idVariabledayplan) return false;
        if (idTranspmap != that.idTranspmap) return false;
        if (idDriver != that.idDriver) return false;
        if (arrivalTime != null ? !arrivalTime.equals(that.arrivalTime) : that.arrivalTime != null) return false;
        if (departureTime != null ? !departureTime.equals(that.departureTime) : that.departureTime != null)
            return false;
        if (numberHours != null ? !numberHours.equals(that.numberHours) : that.numberHours != null) return false;
        if (whereGetCargo != null ? !whereGetCargo.equals(that.whereGetCargo) : that.whereGetCargo != null)
            return false;
        if (whereDeliverCargo != null ? !whereDeliverCargo.equals(that.whereDeliverCargo) : that.whereDeliverCargo != null)
            return false;
        if (denomination != null ? !denomination.equals(that.denomination) : that.denomination != null) return false;
        if (numberTrips != null ? !numberTrips.equals(that.numberTrips) : that.numberTrips != null) return false;
        if (distance != null ? !distance.equals(that.distance) : that.distance != null) return false;
        if (tonsTransported != null ? !tonsTransported.equals(that.tonsTransported) : that.tonsTransported != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idDriverjob ^ (idDriverjob >>> 32));
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (numberHours != null ? numberHours.hashCode() : 0);
        result = 31 * result + (whereGetCargo != null ? whereGetCargo.hashCode() : 0);
        result = 31 * result + (whereDeliverCargo != null ? whereDeliverCargo.hashCode() : 0);
        result = 31 * result + (denomination != null ? denomination.hashCode() : 0);
        result = 31 * result + (numberTrips != null ? numberTrips.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (tonsTransported != null ? tonsTransported.hashCode() : 0);
        result = 31 * result + (int) (idRoadlist ^ (idRoadlist >>> 32));
        result = 31 * result + (int) (idVariabledayplan ^ (idVariabledayplan >>> 32));
        result = 31 * result + (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "IdRoadList", referencedColumnName = "IdRoadList", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "IdVariableDayPlan", referencedColumnName = "IdVariableDayPlan", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "IdTranspMap", referencedColumnName = "IdTranspMap", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "IdDriver", referencedColumnName = "idDriver", nullable = false, insertable = false, updatable = false)})
    public RoadlistEntity getRoadlist() {
        return roadlist;
    }

    public void setRoadlist(RoadlistEntity roadlist) {
        this.roadlist = roadlist;
    }
}
