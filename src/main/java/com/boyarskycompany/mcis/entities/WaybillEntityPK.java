package com.boyarskycompany.mcis.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zandr on 09.10.2016.
 */
public class WaybillEntityPK implements Serializable {
    private long idWaybill;
    private long idDriver;

    @Column(name = "IdWaybill", nullable = false)
    @Id
    public Long getIdWaybill() {
        return idWaybill;
    }

    public void setIdWaybill(Long idWaybill) {
        this.idWaybill = idWaybill;
    }

    @Column(name = "IdDriver", nullable = false)
    @Id
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

        WaybillEntityPK that = (WaybillEntityPK) o;

        if (idWaybill != that.idWaybill) return false;
        if (idDriver != that.idDriver) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idWaybill ^ (idWaybill >>> 32));
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
        return result;
    }
}
