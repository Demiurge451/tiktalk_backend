package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.service.CrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/podcast")
public class PodcastController {
    private final CrudService<Podcast, UUID> podcastService;

    public PodcastController(@Qualifier("podcastServiceImpl") CrudService<Podcast, UUID> podcastService) {
        this.podcastService = podcastService;
    }

    @GetMapping("/")
    public List<Podcast> getPodcasts(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size,
                                     @RequestParam(required = false, defaultValue = "id") String sortParam) {
        return podcastService.getListOfItems(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)));
    }

    @GetMapping("/{id}")
    public Podcast getPodcast(@PathVariable UUID id) {
        return podcastService.getById(id);
    }

    @PostMapping("/")
    public void createPodcast(@RequestBody Podcast podcast) {
        podcastService.save(podcast);
    }

    @DeleteMapping("/{id}")
    public void deletePodcast(@PathVariable UUID id) {
        podcastService.delete(id);
    }

    @PutMapping("/{id}")
    public Podcast updatePodcast(@PathVariable UUID id, @RequestBody Podcast podcast) {
        return null;
    }
}
