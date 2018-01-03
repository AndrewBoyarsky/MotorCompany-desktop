package com.boyarskycompany.mcis.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zandr on 09.10.2016.
 */
public class CargoEntityPK implements Serializable {
    private long idCargo;
    private long idBid;
    private long idAgreement;

    @Column(name = "IdCargo", nullable = false)
    @Id
    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    @Column(name = "IdBid", nullable = false)
    @Id
    public Long getIdBid() {
        return idBid;
    }

    public void setIdBid(Long idBid) {
        this.idBid = idBid;
    }

    @Column(name = "IdAgreement", nullable = false)
    @Id
    public Long getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(Long idAgreement) {
        this.idAgreement = idAgreement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoEntityPK that = (CargoEntityPK) o;

        if (idCargo != that.idCargo) return false;
        if (idBid != that.idBid) return false;
        if (idAgreement != that.idAgreement) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idCargo ^ (idCargo >>> 32));
        result = 31 * result + (int) (idBid ^ (idBid >>> 32));
        result = 31 * result + (int) (idAgreement ^ (idAgreement >>> 32));
        return result;
    }
}
