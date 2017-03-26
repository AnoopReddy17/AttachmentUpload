package com.fileuploadapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fileuploadapp.model.AttachmentResponse;
import com.fileuploadapp.model.FileMetaData;

public interface UploadService {
	
	public ResponseEntity<?> UploadFileService(FileMetaData fileMetaData,MultipartHttpServletRequest request) throws Exception;

}
