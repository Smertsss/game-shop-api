package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.service.ImageStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:4200")
public class ImageController {

    private final ImageStorageService imageStorageService;

    public ImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping("/upload/{entityType}")
    public ResponseEntity<Set<String>> uploadImages(
            @PathVariable String entityType,
            @RequestParam("files") MultipartFile[] files) throws IOException {

        Set<String> savedFileNames = imageStorageService.saveImages(files, entityType);
        return ResponseEntity.ok(savedFileNames);
    }

    @GetMapping("/{entityType}/{imageName}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String entityType,
            @PathVariable String imageName) throws IOException {

        byte[] imageBytes = imageStorageService.getImage(entityType, imageName);

        // Определяем Content-Type на основе расширения файла
        String contentType = determineContentType(imageName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(imageBytes);
    }

    private String determineContentType(String fileName) {
        if (fileName.toLowerCase().endsWith(".png")) {
            return "image/png";
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.toLowerCase().endsWith(".webp")) {
            return "image/webp";
        }
        // По умолчанию JPEG
        return "image/jpeg";
    }
}