package com.boyarskycompany.src.entities;

import javax.persistence.*;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "transpmaptransport", schema = "sql7142192")
@IdClass(TranspmaptransportEntityPK.class)
public class TranspmaptransportEntity {
    private long idTranspmap;
    private long idTransport;
    private TranspmapEntity transpmap;
    private TransportEntity transport;

    @Id
    @Column(name = "IdTranspMap", nullable = false)
    public Long getIdTranspmap() {
        return idTranspmap;
    }

    public void setIdTranspmap(Long idTranspMap) {
        this.idTranspmap = idTranspMap;
    }

    @Id
    @Column(name = "idTransport", nullable = false)
    public Long getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(Long registrationCertificateNumber) {
        this.idTransport = registrationCertificateNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TranspmaptransportEntity that = (TranspmaptransportEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "IdTranspMap", referencedColumnName = "IdTranspMap", nullable = false, insertable = false, updatable = false)
    public TranspmapEntity getTranspmap() {
        return transpmap;
    }

    public void setTranspmap(TranspmapEntity transpmapByIdTranspMap) {
        this.transpmap = transpmapByIdTranspMap;
    }

    @ManyToOne
    @JoinColumn(name = "idTransport", referencedColumnName = "idTransport", nullable = false, insertable = false, updatable = false)
    public TransportEntity getTransport() {
        return transport;
    }

    public void setTransport(TransportEntity transportByRegistrationCertificateNumber) {
        this.transport = transportByRegistrationCertificateNumber;
    }
}
