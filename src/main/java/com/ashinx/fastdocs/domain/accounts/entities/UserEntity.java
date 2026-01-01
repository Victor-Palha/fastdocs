package com.ashinx.fastdocs.domain.accounts.entities;

import com.ashinx.fastdocs.domain.accounts.enums.UserRole;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private UUID id;
    private String username;
    @Email()
    private String email;
    private UUID companyId;
    private UserRole role;
    private String password;

    public static UserEntity create(String username, String email) {
        final UUID id = UUID.randomUUID();
        final UUID companyId = UUID.randomUUID();
        return new UserEntity(id, username, email, companyId, UserRole.ADMIN, null);
    }

    public static UserEntity update(UUID id, String username, String email, UUID companyId, UserRole role) {
        return new UserEntity(id, username, email, companyId, role, null);
    }
}
