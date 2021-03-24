package com.logistic.rms.daoEntities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@ToString(includeFieldNames = true)
@Entity
@Table(name = "ROLE")
@SQLDelete(sql = "update ROLE set is_deleted=TRUE where id = ?")
@Where(clause = "is_deleted = false")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "roleName", nullable = false)
    private String roleName;

    @Column(name = "is_deleted")
    private boolean deletedFalg = false;

    @OneToOne(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private UserRole userRole;
    @Override
    public String getAuthority() {
        return "ROLE_".concat(this.getRoleName());
    }

}