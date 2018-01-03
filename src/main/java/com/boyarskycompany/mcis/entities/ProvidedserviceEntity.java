package com.boyarskycompany.mcis.entities;

import javax.persistence.*;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "providedservice")
@IdClass(ProvidedserviceEntityPK.class)
public class ProvidedserviceEntity {
    private long idProvidedservice;
    private String denominationService;
    private Double numberTonsPerKilometer;
    private Double prizePerTonPerKilometer;
    private Double total;
    private long idRoadlist;
    private long idVariabledayplan;
    private long idTranspmap;
    private long idDriver;
    private RoadlistEntity roadlist;

    @Id
    @Column(name = "IdRoadList", nullable = false)
    public Long getIdRoadlist() {
        return idRoadlist;
    }

    public void setIdRoadlist(Long idRoadList) {
        this.idRoadlist = idRoadList;
    }

    @Basic
    @Column(name = "DenominationService", nullable = true, length = 20)
    public String getDenominationService() {
        return denominationService;
    }

    public void setDenominationService(String denomination) {
        this.denominationService = denomination;
    }

    @Basic
    @Column(name = "NumberTonsPerKilometer", nullable = true, precision = 0)
    public Double getNumberTonsPerKilometer() {
        return numberTonsPerKilometer;
    }

    public void setNumberTonsPerKilometer(Double numberTonsPerKilometer) {
        this.numberTonsPerKilometer = numberTonsPerKilometer;
    }

    @Basic
    @Column(name = "PrizePerTonPerKilometer", nullable = true, precision = 0)
    public Double getPrizePerTonPerKilometer() {
        return prizePerTonPerKilometer;
    }

    public void setPrizePerTonPerKilometer(Double prizePerTonPerKilometer) {
        this.prizePerTonPerKilometer = prizePerTonPerKilometer;
    }

    @Basic
    @Column(name = "Total", nullable = true, precision = 0)
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Id
    @Column(name = "IdVariableDayPlan", nullable = false)
    public Long getIdVariabledayplan() {
        return idVariabledayplan;
    }

    public void setIdVariabledayplan(Long idPlan) {
        this.idVariabledayplan = idPlan;
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

    @Id
    @Column(name = "IdProvidedService", nullable = false)
    public Long getIdProvidedservice() {
        return idProvidedservice;
    }

    public void setIdProvidedservice(Long idProvidedService) {
        this.idProvidedservice = idProvidedService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProvidedserviceEntity that = (ProvidedserviceEntity) o;

        if (idRoadlist != that.idRoadlist) return false;
        if (idVariabledayplan != that.idVariabledayplan) return false;
        if (idTranspmap != that.idTranspmap) return false;
        if (idDriver != that.idDriver) return false;
        if (idProvidedservice != that.idProvidedservice) return false;
        if (denominationService != null ? !denominationService.equals(that.denominationService) : that.denominationService != null) return false;
        if (numberTonsPerKilometer != null ? !numberTonsPerKilometer.equals(that.numberTonsPerKilometer) : that.numberTonsPerKilometer != null)
            return false;
        if (prizePerTonPerKilometer != null ? !prizePerTonPerKilometer.equals(that.prizePerTonPerKilometer) : that.prizePerTonPerKilometer != null)
            return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idRoadlist ^ (idRoadlist >>> 32));
        result = 31 * result + (denominationService != null ? denominationService.hashCode() : 0);
        result = 31 * result + (numberTonsPerKilometer != null ? numberTonsPerKilometer.hashCode() : 0);
        result = 31 * result + (prizePerTonPerKilometer != null ? prizePerTonPerKilometer.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (int) (idVariabledayplan ^ (idVariabledayplan >>> 32));
        result = 31 * result + (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
        result = 31 * result + (int) (idProvidedservice ^ (idProvidedservice >>> 32));
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
