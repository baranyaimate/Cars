package com.pte.cars.user.service.impl;

import com.pte.cars.core.service.impl.CoreCRUDServiceImpl;
import com.pte.cars.user.entity.RoleEntity;
import com.pte.cars.user.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends CoreCRUDServiceImpl<RoleEntity> implements RoleService {

    @Override
    protected void updateCore(RoleEntity persistedEntity, RoleEntity entity) {
        persistedEntity.setAuthority(entity.getAuthority());
    }

    @Override
    protected Class<RoleEntity> getManagedClass() {
        return RoleEntity.class;
    }
}
