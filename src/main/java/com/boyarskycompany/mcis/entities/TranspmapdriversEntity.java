package com.boyarskycompany.mcis.entities;

import javax.persistence.*;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "transpmapdrivers")
@IdClass(TranspmapdriversEntityPK.class)
public class TranspmapdriversEntity {
    private long idTranspmap;
    private long idDriver;
    private TranspmapEntity transpmap;
    private DriverEntity driver;

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

        TranspmapdriversEntity that = (TranspmapdriversEntity) o;

        if (idTranspmap != that.idTranspmap) return false;
        if (idDriver != that.idDriver) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
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
    @JoinColumn(name = "IdDriver", referencedColumnName = "idDriver", nullable = false, insertable = false, updatable = false)
    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity driversByNumberDriverLicense) {
        this.driver = driversByNumberDriverLicense;
    }
}
