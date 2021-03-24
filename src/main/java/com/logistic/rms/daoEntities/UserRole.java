package com.logistic.rms.daoEntities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@ToString(includeFieldNames = true, exclude = {"user","role"})
@Entity
@Table(name = "USER_ROLE")
@SQLDelete(sql = "update USER_ROLE set is_deleted=TRUE where id = ?")
@Where(clause = "is_deleted = false")
public class UserRole {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "is_deleted")
    private boolean deletedFalg = false;
}