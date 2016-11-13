package com.boyarskycompany.src.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "waybill", schema = "sql7142192")
@IdClass(WaybillEntityPK.class)
public class WaybillEntity {
    private long idWaybill;
    private Date date;
    private String consignor;
    private String consignee;
    private Integer cargoSealNumber;
    private String booker;
    private String departmentHead;
    private Double total;
    private Double vat;
    private long idDriver;
    private Long idTotalbill;
    private Long idBid;
    private Long idAgreement;
    private Collection<CargoEntity> cargos;
    private DriverEntity driver;
    private TotalbillEntity totalbill;

    @Id
    @Column(name = "IdWaybill", nullable = false)
    public Long getIdWaybill() {
        return idWaybill;
    }

    public void setIdWaybill(Long idWaybill) {
        this.idWaybill = idWaybill;
    }

    @Basic
    @Column(name = "Date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "Consignor", nullable = true, length = 20)
    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    @Basic
    @Column(name = "Consignee", nullable = true, length = 20)
    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    @Basic
    @Column(name = "CargoSealNumber", nullable = true)
    public Integer getCargoSealNumber() {
        return cargoSealNumber;
    }

    public void setCargoSealNumber(Integer cargoSealNumber) {
        this.cargoSealNumber = cargoSealNumber;
    }

    @Basic
    @Column(name = "Booker", nullable = true, length = 20)
    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    @Basic
    @Column(name = "DepartmentHead", nullable = true, length = 20)
    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    @Basic
    @Column(name = "Total", nullable = true, precision = 0)
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Basic
    @Column(name = "VAT", nullable = true, precision = 0)
    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    @Id
    @Column(name = "IdDriver", nullable = false)
    public Long getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(Long idDriver) {
        this.idDriver = idDriver;
    }

    @Basic
    @Column(name = "IdTotalBill", nullable = true)
    public Long getIdTotalbill() {
        return idTotalbill;
    }

    public void setIdTotalbill(Long idBill) {
        this.idTotalbill = idBill;
    }

    @Basic
    @Column(name = "IdBid", nullable = true)
    public Long getIdBid() {
        return idBid;
    }

    public void setIdBid(Long idBid) {
        this.idBid = idBid;
    }

    @Basic
    @Column(name = "IdAgreement", nullable = true)
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

        WaybillEntity that = (WaybillEntity) o;

        if (idWaybill != that.idWaybill) return false;
        if (idDriver != that.idDriver) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (consignor != null ? !consignor.equals(that.consignor) : that.consignor != null) return false;
        if (consignee != null ? !consignee.equals(that.consignee) : that.consignee != null) return false;
        if (cargoSealNumber != null ? !cargoSealNumber.equals(that.cargoSealNumber) : that.cargoSealNumber != null)
            return false;
        if (booker != null ? !booker.equals(that.booker) : that.booker != null) return false;
        if (departmentHead != null ? !departmentHead.equals(that.departmentHead) : that.departmentHead != null)
            return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (vat != null ? !vat.equals(that.vat) : that.vat != null) return false;
        if (idTotalbill != null ? !idTotalbill.equals(that.idTotalbill) : that.idTotalbill != null) return false;
        if (idBid != null ? !idBid.equals(that.idBid) : that.idBid != null) return false;
        if (idAgreement != null ? !idAgreement.equals(that.idAgreement) : that.idAgreement != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idWaybill ^ (idWaybill >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (consignor != null ? consignor.hashCode() : 0);
        result = 31 * result + (consignee != null ? consignee.hashCode() : 0);
        result = 31 * result + (cargoSealNumber != null ? cargoSealNumber.hashCode() : 0);
        result = 31 * result + (booker != null ? booker.hashCode() : 0);
        result = 31 * result + (departmentHead != null ? departmentHead.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (vat != null ? vat.hashCode() : 0);
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
        result = 31 * result + (idTotalbill != null ? idTotalbill.hashCode() : 0);
        result = 31 * result + (idBid != null ? idBid.hashCode() : 0);
        result = 31 * result + (idAgreement != null ? idAgreement.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "waybill")
    public Collection<CargoEntity> getCargos() {
        return cargos;
    }

    public void setCargos(Collection<CargoEntity> cargos) {
        this.cargos = cargos;
    }

    @ManyToOne
    @JoinColumn(name = "IdDriver", referencedColumnName = "idDriver", nullable = false, insertable = false, updatable = false)
    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity driversByNumberDriverLicense) {
        this.driver = driversByNumberDriverLicense;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "IdTotalBill", referencedColumnName = "IdTotalBill", insertable = false, updatable = false),
            @JoinColumn(name = "IdBid", referencedColumnName = "IdBid", insertable = false, updatable = false),
            @JoinColumn(name = "IdAgreement", referencedColumnName = "IdAgreement", insertable = false, updatable = false)})
    public TotalbillEntity getTotalbill() {
        return totalbill;
    }

    public void setTotalbill(TotalbillEntity totalbill) {
        this.totalbill = totalbill;
    }
}
