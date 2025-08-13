package com.boldenko.game_shop_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageStorageService {

    private final String uploadDirectory;
    private static final Logger log = LoggerFactory.getLogger(ImageStorageService.class);
    private static boolean directoriesInitialized = false;

    public ImageStorageService(@Value("${file.upload.directory}") String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
        log.info("Upload directory set to: {}", uploadDirectory);

        // Создаем базовые директории при инициализации (только один раз)
        initializeDirectories();
    }

    private synchronized void initializeDirectories() {
        if (directoriesInitialized) {
            log.info("Директории уже инициализированы, пропускаем создание");
            return;
        }

        try {
            Path uploadPath = Paths.get(uploadDirectory);

            log.info("Проверка директории для загрузки: {}", uploadPath.toAbsolutePath());

            // Проверяем базовые права только если директория существует
            if (Files.exists(uploadPath)) {
                log.info("Директория существует");
                log.info("Доступна для чтения: {}", Files.isReadable(uploadPath));
                log.info("Доступна для записи: {}", Files.isWritable(uploadPath));

                // Быстрая проверка записи без создания тестового файла
                if (!Files.isWritable(uploadPath)) {
                    log.warn("ВНИМАНИЕ: Директория недоступна для записи!");
                }
            } else {
                log.info("Директория не существует, создаем...");
                Files.createDirectories(uploadPath);
                log.info("Директория создана: {}", uploadPath.toAbsolutePath());
            }

            // Создаем поддиректории только если они не существуют
            createDirectoryIfNotExists("Game");
            createDirectoryIfNotExists("User");
            createDirectoryIfNotExists("Company");

            log.info("Директории изображений готовы к работе");
            directoriesInitialized = true;

        } catch (IOException e) {
            log.error("Критическая ошибка при создании директорий: {}", e.getMessage());
            throw new RuntimeException("Failed to create image directories", e);
        }
    }

    private void createDirectoryIfNotExists(String directoryName) throws IOException {
        Path directoryPath = Paths.get(uploadDirectory, directoryName);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
            log.info("Создана директория: {}", directoryPath.toAbsolutePath());
        } else {
            log.debug("Директория уже существует: {}", directoryName);
        }
    }

    public Set<String> saveImages(MultipartFile[] files, String entityType) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory, entityType);
        log.info("Saving {} images to: {}", files.length, uploadPath.toAbsolutePath());

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            log.info("Created directory: {}", uploadPath.toAbsolutePath());
        }

        return Arrays.stream(files)
                .map(file -> {
                    try {
                        String originalFilename = file.getOriginalFilename();
                        String fileName = UUID.randomUUID() + "_" + originalFilename;
                        Path filePath = uploadPath.resolve(fileName);

                        log.info("Saving file: {} to: {}", originalFilename, filePath.toAbsolutePath());
                        log.info("File size: {} bytes, readable: {}", file.getSize(), !file.isEmpty());

                        Files.copy(file.getInputStream(), filePath);
                        log.info("File saved successfully: {}", fileName);

                        return fileName;
                    } catch (IOException e) {
                        log.error("Failed to store file: {}", file.getOriginalFilename(), e);
                        throw new RuntimeException("Failed to store file: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toSet());
    }

    public byte[] getImage(String entityType, String imageName) throws IOException {
        Path imagePath = Paths.get(uploadDirectory, entityType, imageName);
        if (!Files.exists(imagePath)) {
            throw new IOException("Image not found: " + imagePath);
        }
        return Files.readAllBytes(imagePath);
    }

    public void deleteImage(String entityType, String imageName) throws IOException {
        Path imagePath = Paths.get(uploadDirectory, entityType, imageName);
        Files.deleteIfExists(imagePath);
        log.info("Image deleted: {}", imageName);
    }

    // Метод для ручной проверки (если нужно)
    public String checkPermissions() {
        try {
            Path uploadPath = Paths.get(uploadDirectory);
            StringBuilder result = new StringBuilder();
            result.append("Директория: ").append(uploadPath.toAbsolutePath()).append("\n");
            result.append("Существует: ").append(Files.exists(uploadPath)).append("\n");
            result.append("Чтение: ").append(Files.isReadable(uploadPath)).append("\n");
            result.append("Запись: ").append(Files.isWritable(uploadPath)).append("\n");
            return result.toString();
        } catch (Exception e) {
            return "Ошибка проверки: " + e.getMessage();
        }
    }
}