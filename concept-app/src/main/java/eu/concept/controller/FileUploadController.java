package eu.concept.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.concept.configuration.COnCEPTProperties;
import eu.concept.repository.concept.domain.FileManagement;
import eu.concept.repository.concept.service.FileManagementService;
import eu.concept.repository.concept.service.NotificationService;
import eu.concept.util.other.NotificationTool;
import java.io.IOException;
import java.util.Base64;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Christos Paraskeva <ch.paraskeva at gmail dot com>
 */
@Controller
public class FileUploadController {

    @Autowired
    FileManagementService fmService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    COnCEPTProperties conceptProperties;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody
    String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        String projectID = request.getParameter("projectID");
        LinkedList<FileMeta> files = new LinkedList<>();
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        //Upload each file
        while (itr.hasNext()) {
            //Get next MultipartFile
            mpf = request.getFile(itr.next());
            Logger.getLogger(FileUploadController.class.getName()).log(Level.INFO, "Trying to upload file: {0}", mpf.getOriginalFilename());
            //Create new fileMeta
            FileMeta fileMeta = new FileMeta(mpf.getOriginalFilename(), mpf.getSize() / 1024 + " Kb", mpf.getContentType());
            fileMeta.setStatus("SUCCESS");
            //Create FileManagement object
            FileManagement fm = new FileManagement();
            fm.setPid(Integer.valueOf(projectID));
            fm.setUid(WebController.getCurrentUserCo());
            fm.setFilename(fileMeta.getFileName());
            fm.setType(fileMeta.getFileType());
            //Get bytes[] of uploaded file
            try {
                fileMeta.setBytes(mpf.getBytes());
                String fileContent = "data:".concat(fileMeta.getFileType().concat(";base64,").concat(Base64.getEncoder().encodeToString(mpf.getBytes())));
                fm.setContent(fileContent);
            } catch (IOException e) {
                fileMeta.setStatus("FAIL");
                fileMeta.setMessage("Corrupted file");
                Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, "Could not process file with name: {0} aborting upload...", fileMeta.getFileName());
            }
            //Store file to db
            if ("SUCCESS".equals(fileMeta.getStatus()) && !fmService.storeFile(fm)) {
                fileMeta.setMessage("Could not store file to database");
                fileMeta.setStatus("FAIL");
                Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, "Could not upload file with name: {0}", fileMeta.getFileName());
            }
            files.add(fileMeta);

            if (files.size() > 0) {
                notificationService.storeNotification(Integer.valueOf(projectID), NotificationTool.FM, NotificationTool.NOTIFICATION_OPERATION.UPLOADED, files.size() + " file(s) (" + files.stream().map(s -> s.fileName).collect(Collectors.joining()) + ")", conceptProperties.getFMUploadGenericImageURL(), WebController.getCurrentUserCo());
            }

        }
        return files;
    }

    @JsonIgnoreProperties({"bytes"})
    private class FileMeta {

        private String fileName;
        private String fileSize;
        private String fileType;
        private String status;
        private String message;
        private byte[] bytes;

        //Default constructor
        public FileMeta(String fileName, String fileSize, String fileType) {
            this.fileName = fileName;
            this.fileSize = fileSize;
            this.fileType = fileType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        //setters & getters
        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

    }

}