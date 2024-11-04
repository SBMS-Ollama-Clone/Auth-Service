package com.kkimleang.authservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_permissions", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "unq_name")})
public class Permission extends BaseEntityAudit implements GrantedAuthority, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    public Permission() {
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Permission permission = (Permission) obj;
        return name.equals(permission.name);
    }
}
