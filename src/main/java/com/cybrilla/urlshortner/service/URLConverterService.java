package com.cybrilla.urlshortner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybrilla.urlshortner.domainobject.ShortenUrl;
import com.cybrilla.urlshortner.repository.URLRepository;
import com.cybrilla.urlshortner.service.util.IDConverter;
;

@Service
public class URLConverterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLConverterService.class);
    private final URLRepository urlRepository;

    @Autowired
    public URLConverterService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenURL(String localURL, String longUrl) {
        LOGGER.info("Shortening {}", longUrl);
        ShortenUrl url = new ShortenUrl();
        url.setUrl(longUrl);
        url = urlRepository.save(url);
        
        String uniqueID = IDConverter.createUniqueID(url.getId());
        String baseString = formatLocalURLFromShortener(localURL);
        String shortenedURL = baseString + uniqueID;
        return shortenedURL;
    }

    public String getLongURLFromID(String uniqueID) throws Exception {
        Long id = IDConverter.getDictionaryKeyFromUniqueID(uniqueID);
        String longUrl = urlRepository.findById(id).get().getUrl();
        LOGGER.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
   
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
        }
        sb.append('/');
        return sb.toString();
    }

}
