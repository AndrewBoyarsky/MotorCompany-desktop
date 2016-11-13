package com.boyarskycompany.src.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zandr on 09.10.2016.
 */
public class TranspmaptransportEntityPK implements Serializable {
    private long idTranspmap;
    private long idTransport;

    @Column(name = "IdTranspMap", nullable = false)
    @Id
    public Long getIdTranspmap() {
        return idTranspmap;
    }

    public void setIdTranspmap(Long idTranspMap) {
        this.idTranspmap = idTranspMap;
    }

    @Column(name = "IdTransport", nullable = false)
    @Id
    public Long getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(Long idTransport) {
        this.idTransport = idTransport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TranspmaptransportEntityPK that = (TranspmaptransportEntityPK) o;

        if (idTranspmap != that.idTranspmap) return false;
        if (idTransport != that.idTransport) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (int) (idTransport ^ (idTransport >>> 32));
        return result;
    }
}
