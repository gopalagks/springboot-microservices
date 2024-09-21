package com.gopala.movie_catalog_service.controller;

import com.gopala.movie_catalog_service.model.MovieInfo;
import com.gopala.movie_catalog_service.model.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieInfoController {

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @PostMapping("/movie-info/save")
    public List<MovieInfo> saveAll(@RequestBody List<MovieInfo> movieInfoList){
        return movieInfoRepository.saveAll(movieInfoList);
    }

    @GetMapping("/movie-info/list")

    public List<MovieInfo> getAll(){
        return movieInfoRepository.findAll();
    }

    @GetMapping("movie-info/find-path-by-id/{movieInfoId}")
    public String findPathById(@PathVariable Long movieInfoId){
       var videoInfoOptional = movieInfoRepository.findById(movieInfoId);
       return videoInfoOptional.map(MovieInfo::getPath).orElse(null);
    }
}
