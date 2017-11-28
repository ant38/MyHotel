package com.myhotel.beans.service.security;

import com.myhotel.beans.domain.security.RolePermission;
import com.myhotel.beans.service.BaseService;

import java.io.Serializable;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class RolePermissionsService extends BaseService<RolePermission> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public RolePermissionsService(){
        super(RolePermission.class);
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM RolePermission o", Long.class).getSingleResult();
    }

}
