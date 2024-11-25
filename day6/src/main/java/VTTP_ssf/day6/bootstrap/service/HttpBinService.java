package VTTP_ssf.day6.bootstrap.service;

import java.io.StringReader;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class HttpBinService {

    public static final String GET_URL = "https://httpbin.org/get";
    public static final String POST_URL = "https://httpbin.org/post";
    public static final String JOKES_URL = "https://official-joke-api.appspot.com/jokes/ten";

    // POST METHODS there is a payload,
    public void post() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("name", "apple");
        form.add("qunatity", "%d".formatted(3));

        RequestEntity<MultiValueMap<String, String>> req = RequestEntity
                .post(POST_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(form, MultiValueMap.class);

        RestTemplate template = new RestTemplate();

        try {
            ResponseEntity<String> resp = template.exchange(req, String.class);
            String payload = resp.getBody();
            System.out.printf(">>>>%s\n", payload);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void postJson() {
        // Create Json object
        JsonObject json = Json.createObjectBuilder()
                .add("name", "apple")
                .add("qunatity", "%d".formatted(3))
                .build();

                RequestEntity<String> req = RequestEntity
                .post(POST_URL)
                .contentType(MediaType.APPLICATION_JSON) // same as .header("contentType","application/json")
                .accept(MediaType.APPLICATION_JSON)
                .body(json.toString(), String.class);

        RestTemplate template = new RestTemplate();

        try {
            ResponseEntity<String> resp = template.exchange(req, String.class);
            String payload = resp.getBody();
            System.out.printf(">>>>postJson%s\n", payload);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // GET METHODS no payload
    public void getWithQueryParam() {
        String url = UriComponentsBuilder
                .fromUriString(GET_URL)
                .queryParam("name", "fred")
                .queryParam("email", "fred@gmail.com")
                .toUriString();

        System.out.printf("URL with query param %s\n", url);
        RequestEntity<Void> req = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
    }

    public JsonObject getJokes() {
        // Configure request
        // Get /jokes/ten
        // Accept: application/json
        RequestEntity req = RequestEntity
                .get(JOKES_URL)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        // REST template
        RestTemplate template = new RestTemplate();

        // RESPONSE
        ResponseEntity<String> resp;
        try {
            // Make the call
            resp = template.exchange(req, String.class);
            // Extract payload
            String payload = resp.getBody();

            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonArray result = reader.readArray();
            for (int i = 0; i < result.size(); i++) {
                JsonObject joke = result.getJsonObject(i);
                System.out.printf(">>>Setup: %s\n", joke.getString("setup"));
                System.out.printf(">>>Punchline: %s\n", joke.getString("punchline"));
                System.out.println("<===========================>");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public JsonObject get() {
        // Request Object
        // GET url
        // GET /GET
        // Accept: application/json
        // X-Myheader: ABC123
        RequestEntity req = RequestEntity
                .get(GET_URL)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Myheader", UUID.randomUUID().toString())
                .build();

        // Create instance of RestTemplate
        RestTemplate template = new RestTemplate();

        // Make a call to URL
        // status code ==> 200,300 ==OK
        // status code ==> 400, 500 == ERROR
        try {
            ResponseEntity<String> response = template.exchange(req, String.class);
            String payload = response.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject result = reader.readObject();
            JsonObject headers = result.getJsonObject("headers");
            System.out.printf(">>>Headers %s\n", headers.toString());
            System.out.printf(">>>X-UUID %s\n", headers.getString("X-Myheader").toString());
            // System.out.printf(">>>payload: %s\n",payload);
            return headers;
        } catch (Exception ex) {
            System.out.printf(">>>ERROR :%s\n", ex.getMessage());
        }

        return null;
    }
}
