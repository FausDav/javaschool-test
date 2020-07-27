package com.javaschool.UrlShortener.service;

import com.javaschool.UrlShortener.entity.Url;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Hashtable;

@Service
public class UrlShortenerService {

    static Hashtable urls = new Hashtable();
    static {
        urls.put("asdfg","https://yahoo.com.mx");
        urls.put("abc12","https://google.com");
        urls.put("httpswwwgmdvtv","https://www.gamedev.tv");
    }

    public String create(Url url){
        if (urls.containsValue(url.getUrl())){
            return null;
        } else {
            String shortUrl = RandomStringUtils.randomAlphanumeric(7);
            shortUrl = RandomStringUtils.random(7,true,true);
            urls.put(shortUrl,url.getUrl());

            return shortUrl;
        }
    }

    public String getUrl(String alias){
//        if (urls.containsKey(alias)){
            return (String)urls.get(alias);
//        } else {
//            return null;
//        }
    }
}
