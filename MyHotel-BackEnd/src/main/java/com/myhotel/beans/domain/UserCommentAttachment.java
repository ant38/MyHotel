package com.myhotel.beans.domain;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="UserCommentAttachment")
public class UserCommentAttachment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserCommentAttachment() {
        super();
    }
    
    public UserCommentAttachment(Long id, String fileName) {
        this.setId(id);
        this.fileName = fileName;
    }

    @Size(max = 200)
    private String fileName;
    
    @ManyToOne
    @JoinColumn(name = "USERCOMMENT_ID", referencedColumnName = "ID")
    private UserCommentEntity userComment;

    @Lob
    private byte[] content;

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UserCommentEntity getUserComment() {
        return this.userComment;
    }

    public void setUserComment(UserCommentEntity userComment) {
        this.userComment = userComment;
    }
}
