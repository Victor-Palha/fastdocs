package com.ashinx.fastdocs.domain.accounts.mappers;

import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.infra.persistence.models.UserModel;

public abstract class UserMapper {
    public static UserModel toPersistence(UserEntity data) {
        UserModel user = new UserModel();
        user.setId(data.getId());
        user.setCompanyId(data.getCompanyId());
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setRole(data.getRole());
        return user;
    }

    public static UserEntity toEntity(UserModel data) {
        UserEntity user = new UserEntity();
        user.setId(data.getId());
        user.setCompanyId(data.getCompanyId());
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setRole(data.getRole());
        return user;
    }
}
