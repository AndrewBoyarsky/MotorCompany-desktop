package com.boyarskycompany.mcis.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zandr on 09.10.2016.
 */
public class ProvidedserviceEntityPK implements Serializable {
    private long idProvidedservice;
    private long idRoadlist;
    private long idVariabledayplan;
    private long idTranspmap;
    private long idDriver;

    @Column(name = "IdRoadList", nullable = false)
    @Id
    public Long getIdRoadlist() {
        return idRoadlist;
    }

    public void setIdRoadlist(Long idRoadList) {
        this.idRoadlist = idRoadList;
    }

    @Column(name = "IdVariableDayPlan", nullable = false)
    @Id
    public Long getIdVariabledayplan() {
        return idVariabledayplan;
    }

    public void setIdVariabledayplan(Long idVariableDayPlan) {
        this.idVariabledayplan = idVariableDayPlan;
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

    public void setIdDriver(Long idDriver) {
        this.idDriver = idDriver;
    }

    @Column(name = "IdProvidedService", nullable = false)
    @Id
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

        ProvidedserviceEntityPK that = (ProvidedserviceEntityPK) o;

        if (idRoadlist != that.idRoadlist) return false;
        if (idVariabledayplan != that.idVariabledayplan) return false;
        if (idTranspmap != that.idTranspmap) return false;
        if (idDriver != that.idDriver) return false;
        if (idProvidedservice != that.idProvidedservice) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idRoadlist ^ (idRoadlist >>> 32));
        result = 31 * result + (int) (idVariabledayplan ^ (idVariabledayplan >>> 32));
        result = 31 * result + (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
        result = 31 * result + (int) (idProvidedservice ^ (idProvidedservice >>> 32));
        return result;
    }
}
