package com.javaschool.UrlShortener.service;

import com.javaschool.UrlShortener.entity.Url;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UrlShortenerService {

    static Hashtable<String,String> urls = new Hashtable();

    public Hashtable<String,String> getAll(){
        return urls;
    }


    public String create(Url url){
        if (urls.containsValue(url.getUrl().toLowerCase())){
            return null;
        } else {
            String shortUrl;
            if(url.getUrl().toLowerCase().contains("google")){
                shortUrl = randomString(5,false);
            } else if (url.getUrl().toLowerCase().contains("yahoo")){
                shortUrl = randomString(7,true);
            } else {

                shortUrl = url.getUrl().replaceAll("[^a-z]", "");
            }

            urls.put(shortUrl,url.getUrl().toLowerCase());

            return shortUrl;
        }
    }

    public String getUrl(String alias){
            return (String)urls.get(alias);
    }

    private String randomString(int length,boolean num){

        String string = "";
        int max =  122;
        int min = (num)?49:65;

        Integer[] excludedCodes = {58,59,60,61,62,63,64,91,92,93,94,95,96};

        List<Integer> excludedAscii = Arrays.asList(excludedCodes);

        int random_int;

        for(int i=0;i<length;i++) {
            do{
                random_int = (int) (Math.random() * (max - min + 1) + min);
            } while (excludedAscii.contains(random_int));
            string += (char)random_int;
        }

        return string;
    }
}
