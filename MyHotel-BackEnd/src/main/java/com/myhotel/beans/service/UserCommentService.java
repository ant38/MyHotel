package com.myhotel.beans.service;

import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.UserCommentEntity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceUnitUtil;
import javax.transaction.Transactional;

@Named
public class UserCommentService extends BaseService<UserCommentEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public UserCommentService(){
        super(UserCommentEntity.class);
    }
    
    @Inject
    private UserCommentAttachmentService attachmentService;
    
    @Transactional
    public List<UserCommentEntity> findAllUserCommentEntities() {
        
        return entityManager.createQuery("SELECT o FROM UserComment o LEFT JOIN FETCH o.image", UserCommentEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM UserComment o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(UserCommentEntity userComment) {

        /* This is called before a UserComment is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        this.attachmentService.deleteAttachmentsByUserComment(userComment);
        
    }

    @Transactional
    public List<UserCommentEntity> findAvailableComments(ClientEntity client) {
        return entityManager.createQuery("SELECT o FROM UserComment o WHERE o.client IS NULL", UserCommentEntity.class).getResultList();
    }

    @Transactional
    public List<UserCommentEntity> findCommentsByClient(ClientEntity client) {
        return entityManager.createQuery("SELECT o FROM UserComment o WHERE o.client = :client", UserCommentEntity.class).setParameter("client", client).getResultList();
    }

    @Transactional
    public List<UserCommentEntity> findAvailableComments(HotelEntity hotel) {
        return entityManager.createQuery("SELECT o FROM UserComment o WHERE o.hotel IS NULL", UserCommentEntity.class).getResultList();
    }

    @Transactional
    public List<UserCommentEntity> findCommentsByHotel(HotelEntity hotel) {
        return entityManager.createQuery("SELECT o FROM UserComment o WHERE o.hotel = :hotel", UserCommentEntity.class).setParameter("hotel", hotel).getResultList();
    }

    @Transactional
    public UserCommentEntity lazilyLoadImageToUserComment(UserCommentEntity userComment) {
        PersistenceUnitUtil u = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
        if (!u.isLoaded(userComment, "image") && userComment.getId() != null) {
            userComment = find(userComment.getId());
            userComment.getImage().getId();
        }
        return userComment;
    }
    
}
