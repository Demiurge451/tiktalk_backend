package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/podcast")
public class PodcastController {
    @GetMapping("/")
    public List<Podcast> getPodcasts() {
        return null;
    }

    @GetMapping("/{id}")
    public Podcast getPodcast(@PathVariable UUID id) {
        return null;
    }

    @PostMapping("/")
    public void createPodcast(@RequestBody Podcast podcast) {

    }

    @DeleteMapping("/{id}")
    public void deletePodcast(@PathVariable UUID id) {

    }

    @PutMapping("/{id}")
    public Podcast updatePodcast(@PathVariable UUID id, @RequestBody Podcast podcast) {
        return null;
    }
}
