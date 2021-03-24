package com.logistic.rms.daoEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "RATE")
@EqualsAndHashCode
@ToString(includeFieldNames = true)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update RATE set is_deleted=true where rateid = ?")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "rateid",nullable = false)
    @JsonProperty("rateid")
    private long id;

    @JsonProperty("ratedescription")
    @Column( name = "ratedescription",nullable = true)
    private String description;

    @JsonProperty("Rate_Expiration_Date")
    @Column( name = "rateexpirationdate",nullable = false)
    private Date expirationDate;

    @Column( name = "rateeffectivedate",nullable = false)
    @JsonProperty("Rate_Effective_Date")
    private Date effectiveDate;

    @Column( name = "created_date",nullable = false)
    @CreationTimestamp
    @JsonIgnore
    private Date created;

    @JsonProperty("Amount")
    @Column(name = "amount", nullable = false)
    private float amount;

    @JsonProperty("Surcharge Rate")
    @Transient
    private int surchargeRate;

    @JsonProperty("Surcharge Description")
    @Transient
    private String surchargeDescription;

    @JsonIgnore
    @Column(name = "is_deleted",nullable = true)
    private Boolean is_deleted = false;

}
