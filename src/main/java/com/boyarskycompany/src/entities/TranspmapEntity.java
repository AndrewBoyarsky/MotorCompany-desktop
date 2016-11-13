package com.boyarskycompany.src.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "transpmap", schema = "sql7142192")
public class TranspmapEntity {
    private long idTranspmap;
    private Date issueDateTranspMap;
    private Collection<BidtranspmapEntity> bidtranspmaps;
    private Collection<TranspmapdriversEntity> transpmapdriverss;
    private Collection<TranspmaptransportEntity> transpmaptransports;
    private Collection<VariabledayplanEntity> variabledayplans;

    @Id
    @Column(name = "IdTranspMap", nullable = false)
    public Long getIdTranspmap() {
        return idTranspmap;
    }

    public void setIdTranspmap(Long idTranspMap) {
        this.idTranspmap = idTranspMap;
    }

    @Basic
    @Column(name = "IssueDateTranspMap", nullable = true)
    public Date getIssueDateTranspMap() {
        return issueDateTranspMap;
    }

    public void setIssueDateTranspMap(Date issueDateTranspMap) {
        this.issueDateTranspMap = issueDateTranspMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TranspmapEntity that = (TranspmapEntity) o;

        if (idTranspmap != that.idTranspmap) return false;
        if (issueDateTranspMap != null ? !issueDateTranspMap.equals(that.issueDateTranspMap) : that.issueDateTranspMap != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTranspmap ^ (idTranspmap >>> 32));
        result = 31 * result + (issueDateTranspMap != null ? issueDateTranspMap.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "transpmap")
    public Collection<BidtranspmapEntity> getBidtranspmaps() {
        return bidtranspmaps;
    }

    public void setBidtranspmaps(Collection<BidtranspmapEntity> bidtranspmapsByIdTranspMap) {
        this.bidtranspmaps = bidtranspmapsByIdTranspMap;
    }

    @OneToMany(mappedBy = "transpmap")
    public Collection<TranspmapdriversEntity> getTranspmapdriverss() {
        return transpmapdriverss;
    }

    public void setTranspmapdriverss(Collection<TranspmapdriversEntity> transpmapdriversesByIdTranspMap) {
        this.transpmapdriverss = transpmapdriversesByIdTranspMap;
    }

    @OneToMany(mappedBy = "transpmap")
    public Collection<TranspmaptransportEntity> getTranspmaptransports() {
        return transpmaptransports;
    }

    public void setTranspmaptransports(Collection<TranspmaptransportEntity> transpmaptransportsByIdTranspMap) {
        this.transpmaptransports = transpmaptransportsByIdTranspMap;
    }

    @OneToMany(mappedBy = "transpmap")
    public Collection<VariabledayplanEntity> getVariabledayplans() {
        return variabledayplans;
    }

    public void setVariabledayplans(Collection<VariabledayplanEntity> variabledayplenByIdTranspMap) {
        this.variabledayplans = variabledayplenByIdTranspMap;
    }
}
