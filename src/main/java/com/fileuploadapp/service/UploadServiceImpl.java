package com.fileuploadapp.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileuploadapp.model.FileMetaData;
@Component
public class UploadServiceImpl implements UploadService {
	@Value("${file.path}")
	String fileUploadPath;

	@Override
	public ResponseEntity<?> UploadFileService(FileMetaData fileMetaData,MultipartHttpServletRequest request) throws Exception {
		Map<String, MultipartFile> multipartFileMap = request.getFileMap();
		String originalfilename=null;
		String uploadedFiles="";
		if(multipartFileMap.size()!=0){
		for(String key : multipartFileMap.keySet()){
			originalfilename=multipartFileMap.get(key).getOriginalFilename();
			uploadedFiles=uploadedFiles+originalfilename+",";
			byte [] bytes = multipartFileMap.get(key).getBytes();
			Path path = Paths.get(fileUploadPath+ originalfilename);
	        Files.write(path, bytes);
	        
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(new File(fileUploadPath+fileMetaData.getFileName()+".meta"), fileMetaData);
		
		}
        return new ResponseEntity("Successfully uploaded - "+ uploadedFiles, HttpStatus.OK);
		}else{
			return new ResponseEntity("Please enter file ", HttpStatus.OK);	
		}
		
	}

}
