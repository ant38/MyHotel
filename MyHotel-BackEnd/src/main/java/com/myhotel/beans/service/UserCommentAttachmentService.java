package com.myhotel.beans.service;

import com.myhotel.beans.domain.UserCommentAttachment;
import com.myhotel.beans.domain.UserCommentEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class UserCommentAttachmentService extends BaseService<UserCommentAttachment> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public UserCommentAttachmentService(){
        super(UserCommentAttachment.class);
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM UserCommentAttachment o", Long.class).getSingleResult();
    }

    @Transactional
    public void deleteAttachmentsByUserComment(UserCommentEntity userComment) {
        entityManager
                .createQuery("DELETE FROM UserCommentAttachment c WHERE c.userComment = :p")
                .setParameter("p", userComment).executeUpdate();
    }
    
    @Transactional
    public List<UserCommentAttachment> getAttachmentsList(UserCommentEntity userComment) {
        if (userComment == null || userComment.getId() == null) {
            return new ArrayList<>();
        }
        // The byte streams are not loaded from database with following line. This would cost too much.
        return entityManager.createQuery("SELECT NEW com.myhotel.beans.domain.UserCommentAttachment(o.id, o.fileName) FROM UserCommentAttachment o WHERE o.userComment.id = :id", UserCommentAttachment.class).setParameter("id", userComment.getId()).getResultList();
    }
}
