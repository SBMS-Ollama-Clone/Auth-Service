package com.kkimleang.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_verification_codes")
public class VerificationCode extends BaseEntityAudit {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private User user;

    private String code;
    private Boolean isExpired = false;
    private Boolean isUsed = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VerificationCode that = (VerificationCode) o;
        return Objects.equals(user, that.user) && Objects.equals(code, that.code) && Objects.equals(isExpired, that.isExpired) && Objects.equals(isUsed, that.isUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, code, isExpired, isUsed);
    }
}
