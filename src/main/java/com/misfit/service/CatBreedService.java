package com.misfit.service;

import com.google.gson.Gson;
import com.misfit.entity.Breed;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Cat breed service.
 * Code Credit: https://youtu.be/9oq7Y8n1t00?si=TBQpogWMHC1my8ui
 */
public class CatBreedService {
    /**
     * Gets breed names.
     *
     * @return the breed names
     * @throws URISyntaxException   the uri syntax exception
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public List<String> getBreedNames() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.thecatapi.com/v1/breeds"))
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Breed[] breeds = gson.fromJson(getResponse.body(), Breed[].class);

        List<String> breedNames = new ArrayList<>();
        for (Breed breed : breeds) {
            breedNames.add(breed.getName());
        }

        return breedNames;
    }
}