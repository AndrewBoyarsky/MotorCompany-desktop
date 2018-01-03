package com.boyarskycompany.mcis.entities;

import javax.persistence.*;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "variabledayplandrivers")
@IdClass(VariabledayplandriversEntityPK.class)
public class VariabledayplandriversEntity {
    private long idVariabledayplan;
    private long idTranspmap;
    private long idDriver;
    private VariabledayplanEntity variabledayplan;
    private DriverEntity driver;

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

    public void setIdDriver(Long numberDriverLicense) {
        this.idDriver = numberDriverLicense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariabledayplandriversEntity that = (VariabledayplandriversEntity) o;

        if (idVariabledayplan != that.idVariabledayplan) return false;
        if (idTranspmap != that.idTranspmap) return false;
        if (idDriver != that.idDriver) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idVariabledayplan ^ (idVariabledayplan >>> 32));
        result = 31 * result + (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
        return result;
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
    @JoinColumn(name = "IdDriver", referencedColumnName = "IdDriver", nullable = false, insertable = false, updatable = false)
    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity driversByNumberDriverLicense) {
        this.driver = driversByNumberDriverLicense;
    }
}
