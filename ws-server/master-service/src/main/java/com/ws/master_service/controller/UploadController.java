package com.ws.master_service.controller;

import com.ws.master_service.dto.admin.upload.UploadDto;
import com.ws.master_service.proxy.CloudProxy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {

    private final CloudProxy cloudProxy;

    @PostMapping("/multipart")
    public ResponseEntity<Object> test1(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        return ResponseEntity.ok(cloudProxy.uploadImage(file));
    }

//    @PostMapping("/byte-arr")
//    public ResponseEntity<Object> test2(@RequestBody UploadDto dto) throws IOException, ExecutionException, InterruptedException {
//        return ResponseEntity.ok(cloudProxy.uploadImage(dto.getFile()));
//    }

    @PostMapping("/str")
    public ResponseEntity<Object> test3(@RequestBody UploadDto dto) throws IOException, ExecutionException, InterruptedException {
        byte[] bytes = Base64.getDecoder().decode(dto.getFile());
        return ResponseEntity.ok(cloudProxy.uploadImage(bytes));
    }





}
