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
import java.net.URL;
import java.util.Hashtable;

@RestController
public class UrlShortenerController {

    @Autowired
    UrlShortenerService service;

    @GetMapping("/")
    public String getURL(){
        return  "Testing";
    }

    @GetMapping("/all")
    public ResponseEntity<Hashtable<String,String>> getAllURL(){
        Hashtable list = service.getAll();
        if (list.size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(setErrorMessage("No hay URLs registradas"));
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable("id") String id) {
        if(!isValidId(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Alias no válido");
        }
        String url = service.getUrl(id);
        if (url == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alias no registrado");
        } else {
            return ResponseEntity.ok(url);
        }
    }

    @PostMapping
    public ResponseEntity<Hashtable> create(@RequestBody Url shortedUrl) throws URISyntaxException {

        if(!isValidUrl(shortedUrl.getUrl())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(setErrorMessage("URL inválida"));
        }

        String createdShortUrl = service.create(shortedUrl);
        if (createdShortUrl == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(setErrorMessage("La URL ya está registrada"));
        } else if (createdShortUrl.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(setErrorMessage("La URL no puede ser procesada, se vuelve vacía con el algoritmo utilizado"));
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

    private boolean isValidUrl(String url){
        if(!url.matches("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")){
            try {
                new URL(url).toURI();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return true;
        }
    }

    private Hashtable<String,String> setErrorMessage(String message){
        Hashtable<String,String> hashtable = new Hashtable<>(1);
        hashtable.put("message",message);
        return hashtable;
    }

    private boolean isValidId(String id) {
        if (!id.matches("[a-zA-Z0-9]{1,}")) {
            return false;
        } else {
            return true;
        }
    }
}
