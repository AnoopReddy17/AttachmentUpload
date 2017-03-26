package com.fileuploadapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "upload";
    }
    
    @GetMapping("/fileUpload")
    public String fileUpload() {
        return "fileUpload";
    }
    
    @GetMapping("/fileDownload")
    public String fileDownload() {
        return "fileDownload";
    }
}