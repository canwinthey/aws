package com.canwinthey.controller;

import com.canwinthey.service.AzureStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/azure/file")
public class AzureStorageController {

    @Autowired
    private AzureStorageService azureStorageService;

    @GetMapping("/")
    public List<String> blobitemst() {
        return azureStorageService.listFiles();
    }

    @GetMapping("/download/{filename}")
    public byte[] download(@PathVariable String filename) {
        log.info("download blobitem: {}", filename);
        return azureStorageService.downloadFile(filename).toByteArray();
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file) throws IOException {
        log.info("Filename :" + file.getOriginalFilename());
        log.info("Size:" + file.getSize());
        log.info("Contenttype:" + file.getContentType());
        azureStorageService.storeFile(file.getOriginalFilename(),file.getInputStream(), file.getSize());
        return file.getOriginalFilename() + " Has been saved as a blob-item!!!";
    }
}
