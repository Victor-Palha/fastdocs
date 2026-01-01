package com.ashinx.fastdocs.infra.persistence.models;

import com.ashinx.fastdocs.domain.accounts.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "tb_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
    @Id
    private UUID id;

    private String username;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;
}
