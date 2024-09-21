package com.gopala.movie_streaming_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@CrossOrigin(origins = "*")
public class MovieStreamController {

    public static final String VIDEO_DIRECTORY = "/Users/gopalakumarsingh/Desktop/Java+Spring/microservices/video/";

    @Autowired
    private  MovieCatalogService movieCatalogService;
    @GetMapping("/stream/{videoPath}")
    public ResponseEntity<Resource> streamVideo(
            @PathVariable String videoPath) throws IOException {

        File file = new File(VIDEO_DIRECTORY + videoPath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        long fileSize = file.length();
        Resource videoResource = new FileSystemResource(file);  // Correct way to wrap the file in a Resource

        // Correct MediaType detection from the file
        MediaType mediaType = MediaTypeFactory.getMediaType(String.valueOf(file))
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .contentType(mediaType)  // Use detected media type
                    .contentLength(fileSize)
                    .body(videoResource);  // Return videoResource wrapped as FileSystemResource
    }

    @GetMapping("/stream/with-id/{videoInfoId}")
    public ResponseEntity<Resource> streamVideoById(
            @PathVariable Long videoInfoId) throws IOException {

        // Get the movie path from the MovieCatalogService based on the video ID
        String moviePath = movieCatalogService.getMoviePath(videoInfoId);

        // Call the updated streamVideo method that handles range requests
        return streamVideo(moviePath);
    }

}
