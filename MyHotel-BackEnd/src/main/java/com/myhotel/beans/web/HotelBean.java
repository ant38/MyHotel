package com.myhotel.beans.web;

import com.myhotel.beans.domain.HotelAttachment;
import com.myhotel.beans.domain.HotelEntity;
import com.myhotel.beans.domain.HotelImage;
import com.myhotel.beans.domain.HotelierEntity;
import com.myhotel.beans.domain.RoomEntity;
import com.myhotel.beans.domain.SpecificityEntity;
import com.myhotel.beans.domain.UserCommentEntity;
import com.myhotel.beans.service.HotelAttachmentService;
import com.myhotel.beans.service.HotelService;
import com.myhotel.beans.service.HotelierService;
import com.myhotel.beans.service.RoomService;
import com.myhotel.beans.service.SpecificityService;
import com.myhotel.beans.service.UserCommentService;
import com.myhotel.beans.service.security.SecurityWrapper;
import com.myhotel.beans.web.util.MessageFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named("hotelBean")
@ViewScoped
public class HotelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(HotelBean.class.getName());
    
    private List<HotelEntity> hotelList;

    private HotelEntity hotel;
    
    private List<HotelAttachment> hotelAttachments;
    
    @Inject
    private HotelService hotelService;
    
    @Inject
    private HotelAttachmentService hotelAttachmentService;
    
    UploadedFile uploadedImage;
    byte[] uploadedImageContents;
    
    @Inject
    private HotelierService hotelierService;
    
    @Inject
    private UserCommentService userCommentService;
    
    @Inject
    private SpecificityService specificityService;
    
    @Inject
    private RoomService roomService;
    
    private DualListModel<HotelierEntity> hoteliers;
    private List<String> transferedHotelierIDs;
    private List<String> removedHotelierIDs;
    
    private DualListModel<UserCommentEntity> comments;
    private List<String> transferedCommentIDs;
    private List<String> removedCommentIDs;
    
    private DualListModel<SpecificityEntity> specificitys;
    private List<String> transferedSpecificityIDs;
    private List<String> removedSpecificityIDs;
    
    private DualListModel<RoomEntity> rooms;
    private List<String> transferedRoomIDs;
    private List<String> removedRoomIDs;
    
    public void prepareNewHotel() {
        reset();
        this.hotel = new HotelEntity();
        // set any default values now, if you need
        // Example: this.hotel.setAnything("test");
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
            
                    HotelImage hotelImage = new HotelImage();
                    hotelImage.setContent(baos.toByteArray());
                    hotel.setImage(hotelImage);
                } catch (Exception e) {
                    FacesMessage facesMessage = MessageFactory.getMessage(
                            "message_upload_exception");
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                    FacesContext.getCurrentInstance().validationFailed();
                    return null;
                }
            }
            
            if (hotel.getId() != null) {
                hotel = hotelService.update(hotel);
                message = "message_successfully_updated";
            } else {
                hotel = hotelService.save(hotel);
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
        
        hotelList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            hotelService.delete(hotel);
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
    
    public void onDialogOpen(HotelEntity hotel) {
        reset();
        this.hotel = hotel;
    }
    
    public void reset() {
        hotel = null;
        hotelList = null;
        
        hotelAttachments = null;
        
        hoteliers = null;
        transferedHotelierIDs = null;
        removedHotelierIDs = null;
        
        comments = null;
        transferedCommentIDs = null;
        removedCommentIDs = null;
        
        specificitys = null;
        transferedSpecificityIDs = null;
        removedSpecificityIDs = null;
        
        rooms = null;
        transferedRoomIDs = null;
        removedRoomIDs = null;
        
        uploadedImage = null;
        uploadedImageContents = null;
        
    }

    public DualListModel<HotelierEntity> getHoteliers() {
        return hoteliers;
    }

    public void setHoteliers(DualListModel<HotelierEntity> hoteliers) {
        this.hoteliers = hoteliers;
    }
    
    public List<HotelierEntity> getFullHoteliersList() {
        List<HotelierEntity> allList = new ArrayList<>();
        allList.addAll(hoteliers.getSource());
        allList.addAll(hoteliers.getTarget());
        return allList;
    }
    
    public void onHoteliersDialog(HotelEntity hotel) {
        // Prepare the hotelier PickList
        this.hotel = hotel;
        List<HotelierEntity> selectedHoteliersFromDB = hotelierService
                .findHoteliersByHotel(this.hotel);
        List<HotelierEntity> availableHoteliersFromDB = hotelierService
                .findAvailableHoteliers(this.hotel);
        this.hoteliers = new DualListModel<>(availableHoteliersFromDB, selectedHoteliersFromDB);
        
        transferedHotelierIDs = new ArrayList<>();
        removedHotelierIDs = new ArrayList<>();
    }
    
    public void onHoteliersPickListTransfer(TransferEvent event) {
        // If a hotelier is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((HotelierEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedHotelierIDs.add(id);
                removedHotelierIDs.remove(id);
            } else if (event.isRemove()) {
                removedHotelierIDs.add(id);
                transferedHotelierIDs.remove(id);
            }
        }
        
    }
    
    public void updateHotelier(HotelierEntity hotelier) {
        // If a new hotelier is created, we persist it to the database,
        // but we do not assign it to this hotel in the database, yet.
        hoteliers.getTarget().add(hotelier);
        transferedHotelierIDs.add(hotelier.getId().toString());
    }
    
    public DualListModel<UserCommentEntity> getComments() {
        return comments;
    }

    public void setComments(DualListModel<UserCommentEntity> userComments) {
        this.comments = userComments;
    }
    
    public List<UserCommentEntity> getFullCommentsList() {
        List<UserCommentEntity> allList = new ArrayList<>();
        allList.addAll(comments.getSource());
        allList.addAll(comments.getTarget());
        return allList;
    }
    
    public void onCommentsDialog(HotelEntity hotel) {
        // Prepare the comment PickList
        this.hotel = hotel;
        List<UserCommentEntity> selectedUserCommentsFromDB = userCommentService
                .findCommentsByHotel(this.hotel);
        List<UserCommentEntity> availableUserCommentsFromDB = userCommentService
                .findAvailableComments(this.hotel);
        this.comments = new DualListModel<>(availableUserCommentsFromDB, selectedUserCommentsFromDB);
        
        transferedCommentIDs = new ArrayList<>();
        removedCommentIDs = new ArrayList<>();
    }
    
    public void onCommentsPickListTransfer(TransferEvent event) {
        // If a comment is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((UserCommentEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedCommentIDs.add(id);
                removedCommentIDs.remove(id);
            } else if (event.isRemove()) {
                removedCommentIDs.add(id);
                transferedCommentIDs.remove(id);
            }
        }
        
    }
    
    public void updateComment(UserCommentEntity userComment) {
        // If a new comment is created, we persist it to the database,
        // but we do not assign it to this hotel in the database, yet.
        comments.getTarget().add(userComment);
        transferedCommentIDs.add(userComment.getId().toString());
    }
    
    public DualListModel<SpecificityEntity> getSpecificitys() {
        return specificitys;
    }

    public void setSpecificitys(DualListModel<SpecificityEntity> specificitys) {
        this.specificitys = specificitys;
    }
    
    public List<SpecificityEntity> getFullSpecificitysList() {
        List<SpecificityEntity> allList = new ArrayList<>();
        allList.addAll(specificitys.getSource());
        allList.addAll(specificitys.getTarget());
        return allList;
    }
    
    public void onSpecificitysDialog(HotelEntity hotel) {
        // Prepare the specificity PickList
        this.hotel = hotel;
        List<SpecificityEntity> selectedSpecificitysFromDB = specificityService
                .findSpecificitysByHotel(this.hotel);
        List<SpecificityEntity> availableSpecificitysFromDB = specificityService
                .findAvailableSpecificitys(this.hotel);
        this.specificitys = new DualListModel<>(availableSpecificitysFromDB, selectedSpecificitysFromDB);
        
        transferedSpecificityIDs = new ArrayList<>();
        removedSpecificityIDs = new ArrayList<>();
    }
    
    public void onSpecificitysPickListTransfer(TransferEvent event) {
        // If a specificity is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((SpecificityEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedSpecificityIDs.add(id);
                removedSpecificityIDs.remove(id);
            } else if (event.isRemove()) {
                removedSpecificityIDs.add(id);
                transferedSpecificityIDs.remove(id);
            }
        }
        
    }
    
    public void updateSpecificity(SpecificityEntity specificity) {
        // If a new specificity is created, we persist it to the database,
        // but we do not assign it to this hotel in the database, yet.
        specificitys.getTarget().add(specificity);
        transferedSpecificityIDs.add(specificity.getId().toString());
    }
    
    public DualListModel<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(DualListModel<RoomEntity> rooms) {
        this.rooms = rooms;
    }
    
    public List<RoomEntity> getFullRoomsList() {
        List<RoomEntity> allList = new ArrayList<>();
        allList.addAll(rooms.getSource());
        allList.addAll(rooms.getTarget());
        return allList;
    }
    
    public void onRoomsDialog(HotelEntity hotel) {
        // Prepare the room PickList
        this.hotel = hotel;
        List<RoomEntity> selectedRoomsFromDB = roomService
                .findRoomsByHotel(this.hotel);
        List<RoomEntity> availableRoomsFromDB = roomService
                .findAvailableRooms(this.hotel);
        this.rooms = new DualListModel<>(availableRoomsFromDB, selectedRoomsFromDB);
        
        transferedRoomIDs = new ArrayList<>();
        removedRoomIDs = new ArrayList<>();
    }
    
    public void onRoomsPickListTransfer(TransferEvent event) {
        // If a room is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((RoomEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedRoomIDs.add(id);
                removedRoomIDs.remove(id);
            } else if (event.isRemove()) {
                removedRoomIDs.add(id);
                transferedRoomIDs.remove(id);
            }
        }
        
    }
    
    public void updateRoom(RoomEntity room) {
        // If a new room is created, we persist it to the database,
        // but we do not assign it to this hotel in the database, yet.
        rooms.getTarget().add(room);
        transferedRoomIDs.add(room.getId().toString());
    }
    
    public void onCommentsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            List<UserCommentEntity> selectedUserCommentsFromDB = userCommentService
                    .findCommentsByHotel(this.hotel);
            List<UserCommentEntity> availableUserCommentsFromDB = userCommentService
                    .findAvailableComments(this.hotel);
            
            for (UserCommentEntity userComment : selectedUserCommentsFromDB) {
                if (removedCommentIDs.contains(userComment.getId().toString())) {
                    userComment.setHotel(null);
                    userCommentService.update(userComment);
                }
            }
    
            for (UserCommentEntity userComment : availableUserCommentsFromDB) {
                if (transferedCommentIDs.contains(userComment.getId().toString())) {
                    userComment.setHotel(hotel);
                    userCommentService.update(userComment);
                }
            }
            
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void onSpecificitysSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            List<SpecificityEntity> selectedSpecificitysFromDB = specificityService
                    .findSpecificitysByHotel(this.hotel);
            List<SpecificityEntity> availableSpecificitysFromDB = specificityService
                    .findAvailableSpecificitys(this.hotel);
            
            for (SpecificityEntity specificity : selectedSpecificitysFromDB) {
                if (removedSpecificityIDs.contains(specificity.getId().toString())) {
                    specificity.setHotel(null);
                    specificityService.update(specificity);
                }
            }
    
            for (SpecificityEntity specificity : availableSpecificitysFromDB) {
                if (transferedSpecificityIDs.contains(specificity.getId().toString())) {
                    specificity.setHotel(hotel);
                    specificityService.update(specificity);
                }
            }
            
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void onRoomsSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            List<RoomEntity> selectedRoomsFromDB = roomService
                    .findRoomsByHotel(this.hotel);
            List<RoomEntity> availableRoomsFromDB = roomService
                    .findAvailableRooms(this.hotel);
            
            for (RoomEntity room : selectedRoomsFromDB) {
                if (removedRoomIDs.contains(room.getId().toString())) {
                    room.setHotel(null);
                    roomService.update(room);
                }
            }
    
            for (RoomEntity room : availableRoomsFromDB) {
                if (transferedRoomIDs.contains(room.getId().toString())) {
                    room.setHotel(hotel);
                    roomService.update(room);
                }
            }
            
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void onHoteliersSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<HotelierEntity> selectedHoteliersFromDB = hotelierService.findHoteliersByHotel(this.hotel);
            List<HotelierEntity> availableHoteliersFromDB = hotelierService.findAvailableHoteliers(this.hotel);

            for (HotelierEntity hotelier : selectedHoteliersFromDB) {
                if (removedHotelierIDs.contains(hotelier.getId().toString())) {
                    
                    // Because hotels are lazy loaded, we need to fetch them now
                    hotelier = hotelierService.fetchHotels(hotelier);
                    hotelier.getHotels().remove(hotel);
                    hotelierService.update(hotelier);
                    
                }
            }
    
            for (HotelierEntity hotelier : availableHoteliersFromDB) {
                if (transferedHotelierIDs.contains(hotelier.getId().toString())) {
                    
                    // Because hotels are lazy loaded, we need to fetch them now
                    hotelier = hotelierService.fetchHotels(hotelier);
                    hotelier.getHotels().add(hotel);
                    hotelierService.update(hotelier);
                    
                }
            }
            
            FacesMessage facesMessage = MessageFactory.getMessage("message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
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
        } else if (hotel != null && hotel.getImage() != null) {
            hotel = hotelService.lazilyLoadImageToHotel(hotel);
            return hotel.getImage().getContent();
        }
        return null;
    }
    
    public List<HotelAttachment> getHotelAttachments() {
        if (this.hotelAttachments == null && this.hotel != null && this.hotel.getId() != null) {
            // The byte streams are not loaded from database with following line. This would cost too much.
            this.hotelAttachments = this.hotelAttachmentService.getAttachmentsList(hotel);
        }
        return this.hotelAttachments;
    }
    
    public void handleAttachmentUpload(FileUploadEvent event) {
        
        HotelAttachment hotelAttachment = new HotelAttachment();
        
        try {
            // Would be better to use ...getFile().getContents(), but does not work on every environment
            hotelAttachment.setContent(IOUtils.toByteArray(event.getFile().getInputstream()));
        
            hotelAttachment.setFileName(event.getFile().getFileName());
            hotelAttachment.setHotel(hotel);
            hotelAttachmentService.save(hotelAttachment);
            
            // set hotelAttachment to null, will be refreshed on next demand
            this.hotelAttachments = null;
            
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
            HotelAttachment hotelAttachment) {
        if (hotelAttachment != null && hotelAttachment.getContent() == null) {
            hotelAttachment = hotelAttachmentService
                    .find(hotelAttachment.getId());
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(
                hotelAttachment.getContent()),
                new MimetypesFileTypeMap().getContentType(hotelAttachment
                        .getFileName()), hotelAttachment.getFileName());
    }

    public String deleteAttachment(HotelAttachment attachment) {
        
        hotelAttachmentService.delete(attachment);
        
        // set hotelAttachment to null, will be refreshed on next demand
        this.hotelAttachments = null;
        
        FacesMessage facesMessage = MessageFactory.getMessage(
                "message_successfully_deleted", "Attachment");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return null;
    }
    
    public HotelEntity getHotel() {
        if (this.hotel == null) {
            prepareNewHotel();
        }
        return this.hotel;
    }
    
    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }
    
    public List<HotelEntity> getHotelList() {
        if (hotelList == null) {
            hotelList = hotelService.findAllHotelEntities();
        }
        return hotelList;
    }

    public void setHotelList(List<HotelEntity> hotelList) {
        this.hotelList = hotelList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(HotelEntity hotel, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
