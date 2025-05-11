package com.misfit.service;

import com.google.gson.Gson;
import com.misfit.entity.Breed;
import com.misfit.persistence.PropertiesLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The type Cat breed service.
 * Code Credit: https://youtu.be/9oq7Y8n1t00?si=TBQpogWMHC1my8ui
 */
public class CatBreedService implements PropertiesLoader {
    private String apiURL;

    /**
     * Gets breed names.
     *
     * @return the breed names
     * @throws URISyntaxException   the uri syntax exception
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public List<String> getBreedNames() throws Exception {
        Properties apiProperties = loadProperties("/api.properties");

        this.apiURL = apiProperties.getProperty("api.url");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(apiURL)).build();

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