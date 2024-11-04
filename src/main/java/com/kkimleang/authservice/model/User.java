package com.kkimleang.authservice.model;

import com.kkimleang.authservice.enumeration.AuthProvider;
import com.redis.om.spring.annotations.Indexed;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RedisHash("User")
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "unq_email")})
public class User extends BaseEntityAudit {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    @NotNull
    @Indexed
    private String email;

    @Column(name = "profile_url")
    private String profileURL;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(profileURL, user.profileURL) && Objects.equals(providerId, user.providerId) && Objects.equals(isEnabled, user.isEnabled) && Objects.equals(isVerified, user.isVerified) && provider == user.provider && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, email, profileURL, providerId, isEnabled, isVerified, provider, roles);
    }
}
