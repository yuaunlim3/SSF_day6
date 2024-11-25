package vttp.batch5.ssf.day16.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class urlService {
    @Value("${api.key}")
    private String api_Key;

    public static final String SEARCH_URL = "https://api.giphy.com/v1/gifs/search";

    public String createUrl(String query, String limit, String rating) {
        String url = UriComponentsBuilder
                .fromUriString(SEARCH_URL)
                .queryParam("api_key", api_Key)
                .queryParam("q", query)
                .queryParam("limit", limit)
                .queryParam("offset", "0")
                .queryParam("rating", rating)
                .queryParam("lang", "en")
                .queryParam("bundle", "messaging_non_clips")
                .toUriString();

        return url;
    }
}
