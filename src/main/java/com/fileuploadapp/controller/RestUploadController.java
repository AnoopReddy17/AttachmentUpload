package com.fileuploadapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fileuploadapp.model.FileMetaData;
import com.fileuploadapp.service.UploadService;

@RestController
public class RestUploadController {
	
	@Autowired
	UploadService uploadService;
	
    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

    //Save the uploaded file to this folder
	@Value("${file.path}")
    private String fileDownloadPaTH;

   

    // Multiple file upload
    @PostMapping("/api/upload/uploadFileMultiFiles")
     public ResponseEntity<?> uploadFileMultiFiles(FileMetaData fileMetaData,MultipartHttpServletRequest request, BindingResult result) {

    logger.debug("Multiple file upload....execution begin");
    ResponseEntity<?> responseEntity=null;
    try{
    	responseEntity =uploadService.UploadFileService(fileMetaData,request);
    }catch(Exception e){
    	responseEntity=new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    logger.debug("Multiple file upload...execution completed");
    return responseEntity;
}
    
    @GetMapping("/api/file/download")
    public ResponseEntity<?> download( HttpServletRequest req,HttpServletResponse response, @RequestParam("path") String filePath) {
    	
      /* Path file = Paths.get(fileDownloadPaTH, filePath);
       if (Files.exists(file)) 
       {
           response.addHeader("Content-Disposition", "attachment; filename="+filePath);
           try
           {
               Files.copy(file, response.getOutputStream());
               response.getOutputStream().flush();
           } 
           catch (IOException ex) {
               ex.printStackTrace();
           }
       }*/
        boolean bool = false;
        Resource resource = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        
        try {
        	resource = new FileSystemResource(fileDownloadPaTH+filePath);
        	   return ResponseEntity.ok()
                  		.headers(headers)
                          .contentLength(resource.contentLength())
                          .contentType(MediaType.parseMediaType("application/octet-stream"))
                          .body(new InputStreamResource(resource.getInputStream()));
           
           
           
        } catch(Exception e) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        
    }
   

}
