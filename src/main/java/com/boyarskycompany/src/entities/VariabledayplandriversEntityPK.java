package com.boyarskycompany.src.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zandr on 09.10.2016.
 */
public class VariabledayplandriversEntityPK implements Serializable {
    private long idVariabledayplan;
    private long idTranspmap;
    private long idDriver;

    @Column(name = "IdVariableDayPlan", nullable = false)
    @Id
    public Long getIdVariabledayplan() {
        return idVariabledayplan;
    }

    public void setIdVariabledayplan(Long idPlan) {
        this.idVariabledayplan = idPlan;
    }

    @Column(name = "IdTranspMap", nullable = false)
    @Id
    public Long getIdTranspmap() {
        return idTranspmap;
    }

    public void setIdTranspmap(Long idTranspMap) {
        this.idTranspmap = idTranspMap;
    }

    @Column(name = "IdDriver", nullable = false)
    @Id
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

        VariabledayplandriversEntityPK that = (VariabledayplandriversEntityPK) o;

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
}
