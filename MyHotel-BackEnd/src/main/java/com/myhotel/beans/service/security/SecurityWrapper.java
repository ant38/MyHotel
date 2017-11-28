package com.myhotel.beans.service.security;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.SimpleByteSource;

/**
 * Central wrapping of the Apache Shiro security library
 */
@Named("security")
public class SecurityWrapper implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SecurityWrapper.class.getName());
    
    // Because Shiro does not support public permissions, we need to workaround them:
    private static final Set<String> publicPermissions = 
            new HashSet<>(Arrays.asList(new String[]{"client:create" ,"client:read" ,"client:update" ,"client:delete" ,"client:create" ,"client:read" ,"client:update" ,"client:delete" ,"client:create" ,"client:read" ,"client:update" ,"client:delete" ,"client:create" ,"client:read" ,"client:update" ,"client:delete" ,"client:create" ,"client:read" ,"client:update" ,"client:delete" ,"hotelier:create" ,"hotelier:read" ,"hotelier:update" ,"hotelier:delete" ,"hotelier:create" ,"hotelier:read" ,"hotelier:update" ,"hotelier:delete" ,"hotelier:create" ,"hotelier:read" ,"hotelier:update" ,"hotelier:delete" ,"hotelier:create" ,"hotelier:read" ,"hotelier:update" ,"hotelier:delete" ,"hotelier:create" ,"hotelier:read" ,"hotelier:update" ,"hotelier:delete" ,"userComment:create" ,"userComment:read" ,"userComment:update" ,"userComment:delete" ,"userComment:create" ,"userComment:read" ,"userComment:update" ,"userComment:delete" ,"userComment:create" ,"userComment:read" ,"userComment:update" ,"userComment:delete" ,"userComment:create" ,"userComment:read" ,"userComment:update" ,"userComment:delete" ,"userComment:create" ,"userComment:read" ,"userComment:update" ,"userComment:delete" ,"specificity:create" ,"specificity:read" ,"specificity:update" ,"specificity:delete" ,"specificity:create" ,"specificity:read" ,"specificity:update" ,"specificity:delete" ,"specificity:create" ,"specificity:read" ,"specificity:update" ,"specificity:delete" ,"specificity:create" ,"specificity:read" ,"specificity:update" ,"specificity:delete" ,"specificity:create" ,"specificity:read" ,"specificity:update" ,"specificity:delete" ,"hotel:create" ,"hotel:read" ,"hotel:update" ,"hotel:delete" ,"hotel:create" ,"hotel:read" ,"hotel:update" ,"hotel:delete" ,"hotel:create" ,"hotel:read" ,"hotel:update" ,"hotel:delete" ,"hotel:create" ,"hotel:read" ,"hotel:update" ,"hotel:delete" ,"hotel:create" ,"hotel:read" ,"hotel:update" ,"hotel:delete" ,"room:create" ,"room:read" ,"room:update" ,"room:delete" ,"room:create" ,"room:read" ,"room:update" ,"room:delete" ,"room:create" ,"room:read" ,"room:update" ,"room:delete" ,"room:create" ,"room:read" ,"room:update" ,"room:delete" ,"room:create" ,"room:read" ,"room:update" ,"room:delete" ,"booking:create" ,"booking:read" ,"booking:update" ,"booking:delete" ,"booking:create" ,"booking:read" ,"booking:update" ,"booking:delete" ,"booking:create" ,"booking:read" ,"booking:update" ,"booking:delete" ,"booking:create" ,"booking:read" ,"booking:update" ,"booking:delete" ,"booking:create" ,"booking:read" ,"booking:update" ,"booking:delete" ,"offer:create" ,"offer:read" ,"offer:update" ,"offer:delete" ,"offer:create" ,"offer:read" ,"offer:update" ,"offer:delete" ,"offer:create" ,"offer:read" ,"offer:update" ,"offer:delete" ,"offer:create" ,"offer:read" ,"offer:update" ,"offer:delete" ,"offer:create" ,"offer:read" ,"offer:update" ,"offer:delete" ,"save:create" ,"save:read" ,"save:update" ,"save:delete" ,"save:create" ,"save:read" ,"save:update" ,"save:delete" ,"save:create" ,"save:read" ,"save:update" ,"save:delete" ,"save:create" ,"save:read" ,"save:update" ,"save:delete" ,"save:create" ,"save:read" ,"save:update" ,"save:delete"}));
    
    public static boolean login(String username, String password, boolean rememberMe) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, rememberMe));
        } catch (AuthenticationException e) {
            logger.log(Level.WARNING, "AuthenticationException", e);
            return false;
        }
        return true;
    }
    
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
    
    public static String getUsername() {
        
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            return null;
        }
        
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
    
    public static boolean isPermitted(String permission) {
        return publicPermissions.contains(permission)
                || SecurityUtils.getSubject().isPermitted(permission);
    }

    public static boolean hasReadPermissionOnlyOwner(String entity) {
        return !SecurityUtils.getSubject().isPermitted(entity + ":read")
                && SecurityUtils.getSubject().isPermitted(entity + ":read:owner");
    }
    
    public static String generateSalt() {
        return new BigInteger(250, new SecureRandom()).toString(32);
    }
    
    public static String hashPassword(String password, String salt) {
        Sha512Hash hash = new Sha512Hash(password, (new SimpleByteSource(salt)).getBytes());
        return hash.toHex();
    }
}
