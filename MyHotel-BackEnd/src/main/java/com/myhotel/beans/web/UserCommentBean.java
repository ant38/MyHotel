package com.myhotel.beans.web;

import com.myhotel.beans.domain.ClientEntity;
import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.UserCommentAttachment;
import com.myhotel.beans.domain.UserCommentEntity;
import com.myhotel.beans.domain.UserCommentImage;
import com.myhotel.beans.service.ClientService;
import com.myhotel.beans.service.HotelService;
import com.myhotel.beans.service.UserCommentAttachmentService;
import com.myhotel.beans.service.UserCommentService;
import com.myhotel.beans.service.security.SecurityWrapper;
import com.myhotel.beans.web.util.MessageFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.MimetypesFileTypeMap;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named("userCommentBean")
@ViewScoped
public class UserCommentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserCommentBean.class.getName());
    
    private List<UserCommentEntity> userCommentList;

    private UserCommentEntity userComment;
    
    private List<UserCommentAttachment> userCommentAttachments;
    
    @Inject
    private UserCommentService userCommentService;
    
    @Inject
    private UserCommentAttachmentService userCommentAttachmentService;
    
    UploadedFile uploadedImage;
    byte[] uploadedImageContents;
    
    @Inject
    private ClientService clientService;
    
    @Inject
    private HotelService hotelService;
    
    private List<ClientEntity> allClientsList;
    
    private List<HotelEntity> allHotelsList;
    
    public void prepareNewUserComment() {
        reset();
        this.userComment = new UserCommentEntity();
        // set any default values now, if you need
        // Example: this.userComment.setAnything("test");
    }

    public String persist() {

        String message;
        
        try {
            
            if (this.uploadedImage != null) {
                try {

                    BufferedImage image;
                    try (InputStream in = new ByteArrayInputStream(uploadedImageContents)) {
                        image = ImageIO.read(in);
                    }
                    image = Scalr.resize(image, Method.BALANCED, 300);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageOutputStream imageOS = ImageIO.createImageOutputStream(baos);
                    Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(
                            uploadedImage.getContentType());
                    ImageWriter imageWriter = (ImageWriter) imageWriters.next();
                    imageWriter.setOutput(imageOS);
                    imageWriter.write(image);
                    
                    baos.close();
                    imageOS.close();
                    
                    logger.log(Level.INFO, "Resized uploaded image from {0} to {1}", new Object[]{uploadedImageContents.length, baos.toByteArray().length});
            
                    UserCommentImage userCommentImage = new UserCommentImage();
                    userCommentImage.setContent(baos.toByteArray());
                    userComment.setImage(userCommentImage);
                } catch (Exception e) {
                    FacesMessage facesMessage = MessageFactory.getMessage(
                            "message_upload_exception");
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                    FacesContext.getCurrentInstance().validationFailed();
                    return null;
                }
            }
            
            if (userComment.getId() != null) {
                userComment = userCommentService.update(userComment);
                message = "message_successfully_updated";
            } else {
                userComment = userCommentService.save(userComment);
                message = "message_successfully_created";
            }
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_optimistic_locking_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_save_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        
        userCommentList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            userCommentService.delete(userComment);
            message = "message_successfully_deleted";
            reset();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));
        
        return null;
    }
    
    public void onDialogOpen(UserCommentEntity userComment) {
        reset();
        this.userComment = userComment;
    }
    
    public void reset() {
        userComment = null;
        userCommentList = null;
        
        userCommentAttachments = null;
        
        allClientsList = null;
        
        allHotelsList = null;
        
        uploadedImage = null;
        uploadedImageContents = null;
        
    }

    // Get a List of all client
    public List<ClientEntity> getClients() {
        if (this.allClientsList == null) {
            this.allClientsList = clientService.findAllClientEntities();
        }
        return this.allClientsList;
    }
    
    // Update client of the current userComment
    public void updateClient(ClientEntity client) {
        this.userComment.setClient(client);
        // Maybe we just created and assigned a new client. So reset the allClientList.
        allClientsList = null;
    }
    
    // Get a List of all hotel
    public List<HotelEntity> getHotels() {
        if (this.allHotelsList == null) {
            this.allHotelsList = hotelService.findAllHotelEntities();
        }
        return this.allHotelsList;
    }
    
    // Update hotel of the current userComment
    public void updateHotel(HotelEntity hotel) {
        this.userComment.setHotel(hotel);
        // Maybe we just created and assigned a new hotel. So reset the allHotelList.
        allHotelsList = null;
    }
    
    public void handleImageUpload(FileUploadEvent event) {
        
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(
                event.getFile().getContentType());
        if (!imageWriters.hasNext()) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_image_type_not_supported");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return;
        }
        
        this.uploadedImage = event.getFile();
        this.uploadedImageContents = event.getFile().getContents();
        
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public byte[] getUploadedImageContents() {
        if (uploadedImageContents != null) {
            return uploadedImageContents;
        } else if (userComment != null && userComment.getImage() != null) {
            userComment = userCommentService.lazilyLoadImageToUserComment(userComment);
            return userComment.getImage().getContent();
        }
        return null;
    }
    
    public List<UserCommentAttachment> getUserCommentAttachments() {
        if (this.userCommentAttachments == null && this.userComment != null && this.userComment.getId() != null) {
            // The byte streams are not loaded from database with following line. This would cost too much.
            this.userCommentAttachments = this.userCommentAttachmentService.getAttachmentsList(userComment);
        }
        return this.userCommentAttachments;
    }
    
    public void handleAttachmentUpload(FileUploadEvent event) {
        
        UserCommentAttachment userCommentAttachment = new UserCommentAttachment();
        
        try {
            // Would be better to use ...getFile().getContents(), but does not work on every environment
            userCommentAttachment.setContent(IOUtils.toByteArray(event.getFile().getInputstream()));
        
            userCommentAttachment.setFileName(event.getFile().getFileName());
            userCommentAttachment.setUserComment(userComment);
            userCommentAttachmentService.save(userCommentAttachment);
            
            // set userCommentAttachment to null, will be refreshed on next demand
            this.userCommentAttachments = null;
            
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_successfully_uploaded");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_upload_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }

    public StreamedContent getAttachmentStreamedContent(
            UserCommentAttachment userCommentAttachment) {
        if (userCommentAttachment != null && userCommentAttachment.getContent() == null) {
            userCommentAttachment = userCommentAttachmentService
                    .find(userCommentAttachment.getId());
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(
                userCommentAttachment.getContent()),
                new MimetypesFileTypeMap().getContentType(userCommentAttachment
                        .getFileName()), userCommentAttachment.getFileName());
    }

    public String deleteAttachment(UserCommentAttachment attachment) {
        
        userCommentAttachmentService.delete(attachment);
        
        // set userCommentAttachment to null, will be refreshed on next demand
        this.userCommentAttachments = null;
        
        FacesMessage facesMessage = MessageFactory.getMessage(
                "message_successfully_deleted", "Attachment");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return null;
    }
    
    public UserCommentEntity getUserComment() {
        if (this.userComment == null) {
            prepareNewUserComment();
        }
        return this.userComment;
    }
    
    public void setUserComment(UserCommentEntity userComment) {
        this.userComment = userComment;
    }
    
    public List<UserCommentEntity> getUserCommentList() {
        if (userCommentList == null) {
            userCommentList = userCommentService.findAllUserCommentEntities();
        }
        return userCommentList;
    }

    public void setUserCommentList(List<UserCommentEntity> userCommentList) {
        this.userCommentList = userCommentList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(UserCommentEntity userComment, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
