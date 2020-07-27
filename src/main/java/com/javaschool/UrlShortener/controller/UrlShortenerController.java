package com.javaschool.UrlShortener.controller;

import com.javaschool.UrlShortener.entity.Url;
import com.javaschool.UrlShortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

@RestController
public class UrlShortenerController {

    @Autowired
    UrlShortenerService service;

    @GetMapping("/")
    public String getURL(){
        return  "Testing";
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable("id") String id) {
        String url = service.getUrl(id);
        if (url == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(url);
        }
    }

    @PostMapping
    public ResponseEntity<Hashtable> create(@RequestBody Url shortedUrl) throws URISyntaxException {
        String createdShortUrl = service.create(shortedUrl);
        if (createdShortUrl == null) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        } else {
            Hashtable<String,String> alias = new Hashtable<>(1);
            alias.put("alias",createdShortUrl);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{url}")
                    .buildAndExpand(createdShortUrl)
                    .toUri();

            ResponseEntity response = ResponseEntity.created(uri)
                    .body(alias);
            return response;
        }
    }
}
