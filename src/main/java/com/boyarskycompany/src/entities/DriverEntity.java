package com.boyarskycompany.src.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by zandr on 09.10.2016.
 */
@Entity
@Table(name = "driver", schema = "sql7142192")
public class DriverEntity {
    private long idDriver;
    private String name;
    private String surname;
    private String patronymic;
    private String telephone;
    private Date dateOfRecr;
    private int experiance;
    private String category;
    private Date dateOfIssueCategory;
    private Date dateOfIssueLicense;
    private Date birthDate;
    private String bloodType;
    private String rhFactor;
    private Boolean isWorking;
    private Collection<RoadlistEntity> roadlists;
    private Collection<TranspmapdriversEntity> transpmapdriverss;
    private Collection<VariabledayplandriversEntity> variabledayplandriverss;
    private Collection<WaybillEntity> waybills;

    @Basic
    @Column(name = "Name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "IsWorking", nullable = false)
    public Boolean getIsWorking() {
        return isWorking;
    }

    public void setIsWorking(Boolean isWorking) {
        this.isWorking = isWorking;
    }

    @Basic
    @Column(name = "Surname", nullable = false, length = 20)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "Patronymic", nullable = false, length = 20)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Id
    @Column(name = "idDriver", nullable = false)
    public Long getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(Long idDriver) {
        this.idDriver = idDriver;
    }

    @Basic
    @Column(name = "Telephone", nullable = true, length = 20)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "DateOfRecr", nullable = false)
    public Date getDateOfRecr() {
        return dateOfRecr;
    }

    public void setDateOfRecr(Date dateOfRecr) {
        this.dateOfRecr = dateOfRecr;
    }

    @Basic
    @Column(name = "Experiance", nullable = false)
    public Integer getExperiance() {
        return experiance;
    }

    public void setExperiance(Integer experiance) {
        this.experiance = experiance;
    }

    @Basic
    @Column(name = "Category", nullable = true, length = 20)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "DateOfIssueCategory", nullable = true)
    public Date getDateOfIssueCategory() {
        return dateOfIssueCategory;
    }

    public void setDateOfIssueCategory(Date dateOfIssueCategory) {
        this.dateOfIssueCategory = dateOfIssueCategory;
    }

    @Basic
    @Column(name = "DateOfIssueLicense", nullable = true)
    public Date getDateOfIssueLicense() {
        return dateOfIssueLicense;
    }

    public void setDateOfIssueLicense(Date dateOfIssueLicense) {
        this.dateOfIssueLicense = dateOfIssueLicense;
    }

    @Basic
    @Column(name = "BirthDate", nullable = true)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Basic
    @Column(name = "BloodType", nullable = true, length = 20)
    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    @Basic
    @Column(name = "RhFactor", nullable = true, length = 20)
    public String getRhFactor() {
        return rhFactor;
    }

    public void setRhFactor(String rhFactor) {
        this.rhFactor = rhFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverEntity that = (DriverEntity) o;

        if (idDriver != that.idDriver) return false;
        if (experiance != that.experiance) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(that.patronymic) : that.patronymic != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (dateOfRecr != null ? !dateOfRecr.equals(that.dateOfRecr) : that.dateOfRecr != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (dateOfIssueCategory != null ? !dateOfIssueCategory.equals(that.dateOfIssueCategory) : that.dateOfIssueCategory != null)
            return false;
        if (dateOfIssueLicense != null ? !dateOfIssueLicense.equals(that.dateOfIssueLicense) : that.dateOfIssueLicense != null)
            return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (bloodType != null ? !bloodType.equals(that.bloodType) : that.bloodType != null) return false;
        if (rhFactor != null ? !rhFactor.equals(that.rhFactor) : that.rhFactor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (int) (idDriver ^ (idDriver >>> 32));
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (dateOfRecr != null ? dateOfRecr.hashCode() : 0);
        result = 31 * result + experiance;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (dateOfIssueCategory != null ? dateOfIssueCategory.hashCode() : 0);
        result = 31 * result + (dateOfIssueLicense != null ? dateOfIssueLicense.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (bloodType != null ? bloodType.hashCode() : 0);
        result = 31 * result + (rhFactor != null ? rhFactor.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "driver")
    public Collection<RoadlistEntity> getRoadlists() {
        return roadlists;
    }

    public void setRoadlists(Collection<RoadlistEntity> roadlistsByNumberDriverLicense) {
        this.roadlists = roadlistsByNumberDriverLicense;
    }

    @OneToMany(mappedBy = "driver")
    public Collection<TranspmapdriversEntity> getTranspmapdriverss() {
        return transpmapdriverss;
    }

    public void setTranspmapdriverss(Collection<TranspmapdriversEntity> transpmapdriversesByNumberDriverLicense) {
        this.transpmapdriverss = transpmapdriversesByNumberDriverLicense;
    }

    @OneToMany(mappedBy = "driver")
    public Collection<VariabledayplandriversEntity> getVariabledayplandriverss() {
        return variabledayplandriverss;
    }

    public void setVariabledayplandriverss(Collection<VariabledayplandriversEntity> variabledayplandriversesByNumberDriverLicense) {
        this.variabledayplandriverss = variabledayplandriversesByNumberDriverLicense;
    }

    @OneToMany(mappedBy = "driver")
    public Collection<WaybillEntity> getWaybills() {
        return waybills;
    }

    public void setWaybills(Collection<WaybillEntity> waybillsByNumberDriverLicense) {
        this.waybills = waybillsByNumberDriverLicense;
    }
}
