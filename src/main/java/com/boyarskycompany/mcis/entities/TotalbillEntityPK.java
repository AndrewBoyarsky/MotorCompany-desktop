package com.boyarskycompany.mcis.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zandr on 09.10.2016.
 */
public class TotalbillEntityPK implements Serializable {
    private long idTotalbill;
    private long idBid;
    private long idAgreement;

    @Column(name = "IdTotalBill", nullable = false)
    @Id
    public Long getIdTotalbill() {
        return idTotalbill;
    }

    public void setIdTotalbill(Long idBill) {
        this.idTotalbill = idBill;
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

        TotalbillEntityPK that = (TotalbillEntityPK) o;

        if (idTotalbill != that.idTotalbill) return false;
        if (idBid != that.idBid) return false;
        if (idAgreement != that.idAgreement) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTotalbill ^ (idTotalbill >>> 32));
        result = 31 * result + (int) (idBid ^ (idBid >>> 32));
        result = 31 * result + (int) (idAgreement ^ (idAgreement >>> 32));
        return result;
    }
}
