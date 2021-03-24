package com.logistic.rms.daoEntities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString(includeFieldNames = true, exclude = {"password"})
@Entity
@Table(name = "USER")
@SQLDelete(sql = "update USER set is_deleted=TRUE where id = ?")
@Where(clause = "is_deleted = false")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false)
    private String userName;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "accountnonexpired", nullable = false)
    private Boolean accountNonExpired;

    @Column(name = "accountnonlocked", nullable = false)
    private Boolean accountNonLocked;

    @Column(name = "credentialsnonexpired", nullable = false)
    private Boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "is_deleted")
    private boolean deletedFalg = false;

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private Date userCreationDate;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserRole> userRoleList = new ArrayList<>();
}
