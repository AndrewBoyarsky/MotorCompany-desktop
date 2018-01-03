package com.boyarskycompany.mcis.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "agreement")
public class AgreementEntity {
    private long idAgreement;
    private int agreementValidity;
    private Date contractDate;
    private String carrier;
    private String client;
    private Collection<BidEntity> bids;

    @Id
    @Column(name = "IdAgreement", nullable = false)
    public Long getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(Long idAgreement) {
        this.idAgreement = idAgreement;
    }

    @Basic
    @Column(name = "AgreementValidity", nullable = false)
    public Integer getAgreementValidity() {
        return agreementValidity;
    }

    public void setAgreementValidity(Integer agreementValidity) {
        this.agreementValidity = agreementValidity;
    }

    @Basic
    @Column(name = "ContractDate", nullable = false)
    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    @Basic
    @Column(name = "Carrier", nullable = true, length = 20)
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Basic
    @Column(name = "Client", nullable = true, length = 20)
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgreementEntity that = (AgreementEntity) o;

        if (idAgreement != that.idAgreement) return false;
        if (agreementValidity != that.agreementValidity) return false;
        if (contractDate != null ? !contractDate.equals(that.contractDate) : that.contractDate != null) return false;
        if (carrier != null ? !carrier.equals(that.carrier) : that.carrier != null) return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idAgreement ^ (idAgreement >>> 32));
        result = 31 * result + agreementValidity;
        result = 31 * result + (contractDate != null ? contractDate.hashCode() : 0);
        result = 31 * result + (carrier != null ? carrier.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "agreement")
    public Collection<BidEntity> getBids() {
        return bids;
    }

    public void setBids(Collection<BidEntity> bidsByIdAgreement) {
        this.bids = bidsByIdAgreement;
    }
}
