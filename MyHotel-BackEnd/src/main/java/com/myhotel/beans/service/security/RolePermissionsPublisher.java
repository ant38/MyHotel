package com.myhotel.beans.service.security;

import com.myhotel.beans.domain.security.RolePermission;
import com.myhotel.beans.domain.security.UserRole;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class RolePermissionsPublisher {

    private static final Logger logger = Logger.getLogger(RolePermissionsPublisher.class.getName());
    
    @Inject
    private RolePermissionsService rolePermissionService;
    
    @PostConstruct
    public void postConstruct() {

        if (rolePermissionService.countAllEntries() == 0) {

            rolePermissionService.save(new RolePermission(UserRole.Administrator, "user:*"));
            
            logger.info("Successfully created permissions for user roles.");
        }
    }
}
