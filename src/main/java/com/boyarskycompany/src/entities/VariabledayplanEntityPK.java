package com.boyarskycompany.src.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zandr on 09.10.2016.
 */
public class VariabledayplanEntityPK implements Serializable {
    private long idVariabledayplan;
    private long idTranspmap;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariabledayplanEntityPK that = (VariabledayplanEntityPK) o;

        if (idVariabledayplan != that.idVariabledayplan) return false;
        if (idTranspmap != that.idTranspmap) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idVariabledayplan ^ (idVariabledayplan >>> 32));
        result = 31 * result + (int) (idTranspmap ^ (idTranspmap >>> 32));
        return result;
    }
}
