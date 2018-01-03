package com.boyarskycompany.mcis.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "variabledayplan")
@IdClass(VariabledayplanEntityPK.class)
public class VariabledayplanEntity {
    private long idVariabledayplan;
    private Date date;
    private long idTranspmap;
    private Collection<RoadlistEntity> roadlists;
    private TranspmapEntity transpmap;
    private Collection<VariabledayplandriversEntity> variabledayplandriverss;

    @Id
    @Column(name = "IdVariableDayPlan", nullable = false)
    public Long getIdVariabledayplan() {
        return idVariabledayplan;
    }

    public void setIdVariabledayplan(Long idVariableDayPlan) {
        this.idVariabledayplan = idVariableDayPlan;
    }

    @Basic
    @Column(name = "Date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
    @Id
    @Column(name = "IdTranspMap", nullable = false)
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

        VariabledayplanEntity that = (VariabledayplanEntity) o;

        if (idVariabledayplan != that.idVariabledayplan) return false;
        if (idTranspmap != that.idTranspmap) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idVariabledayplan ^ (idVariabledayplan >>> 32));
        
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (idTranspmap ^ (idTranspmap >>> 32));
        return result;
    }

    @OneToMany(mappedBy = "variabledayplan")
    public Collection<RoadlistEntity> getRoadlists() {
        return roadlists;
    }

    public void setRoadlists(Collection<RoadlistEntity> roadlists) {
        this.roadlists = roadlists;
    }

    @ManyToOne
    @JoinColumn(name = "IdTranspMap", referencedColumnName = "IdTranspMap", nullable = false, insertable = false, updatable = false)
    public TranspmapEntity getTranspmap() {
        return transpmap;
    }

    public void setTranspmap(TranspmapEntity transpmapByIdTranspMap) {
        this.transpmap = transpmapByIdTranspMap;
    }

    @OneToMany(mappedBy = "variabledayplan")
    public Collection<VariabledayplandriversEntity> getVariabledayplandriverss() {
        return variabledayplandriverss;
    }

    public void setVariabledayplandriverss(Collection<VariabledayplandriversEntity> variabledayplandriverses) {
        this.variabledayplandriverss = variabledayplandriverses;
    }
}
