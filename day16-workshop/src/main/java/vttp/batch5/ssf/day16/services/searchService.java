package vttp.batch5.ssf.day16.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class searchService {
    private final Logger logger = Logger.getLogger(searchService.class.getName());
    @Autowired
    private urlService urlSvc;

    public List<String> getQuery(String query, String limit, String rating) {
        List<String> urlList = new ArrayList<>();
        String url = urlSvc.createUrl(query, limit, rating);
        System.out.printf("URL with query param %s\n", url);

        // Construct the request
        RequestEntity<Void> req = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        // REST template
        RestTemplate template = new RestTemplate();

        // Get response body
        ResponseEntity<String> resp;
        try {
            resp = template.exchange(req, String.class);
            String payload = resp.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject result = reader.readObject();
            JsonArray datas = result.getJsonArray("data");
            for (int i = 0; i < datas.size(); i++) {
                JsonObject data = datas.getJsonObject(i);
                JsonObject images = data.getJsonObject("images");
                JsonObject fixedHeight = images.getJsonObject("fixed_height");
                String imageUrl = fixedHeight.getString("url");

                /*
                 * Using stream
                 * String imageUrl = 
                 */
                urlList.add(imageUrl);

            }
            return urlList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return urlList;
        }

    }
}
