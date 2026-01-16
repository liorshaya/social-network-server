package com.server.social_network_server.controllers;

import com.server.social_network_server.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private CloudinaryService cloudinaryService;

    // הכתובת תהיה: POST http://localhost:8080/posts/create
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPost(
            @RequestParam("file") MultipartFile file,        // הקובץ עצמו
            @RequestParam("description") String description, // הטקסט של הפוסט
            @RequestParam("userId") Long userId              // ה-ID של המשתמש
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 1. בדיקה שהקובץ לא ריק
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "File is empty");
                return ResponseEntity.badRequest().body(response);
            }

            // 2. שליחה ל-Cloudinary דרך הסרביס שלך
            String imageUrl = cloudinaryService.uploadImage(file);

            if (imageUrl == null) {
                response.put("success", false);
                response.put("message", "Upload failed");
                return ResponseEntity.status(500).body(response);
            }

            // --- כאן יבוא השלב של שמירה ל-MySQL (נדבר על זה אח"כ) ---
            // Post newPost = new Post(userId, description, imageUrl);
            // postRepository.save(newPost);
            // ---------------------------------------------------------

            // 3. החזרת תשובה לריאקט
            response.put("success", true);
            response.put("message", "Uploaded successfully!");
            response.put("imageUrl", imageUrl); // מחזירים את הלינק כדי שתוכל לראות שזה עבד

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}