package com.misfit.service;

import com.google.gson.Gson;
import com.misfit.entity.Breed;
import com.misfit.persistence.PropertiesLoader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The `CatBreedService` class retrieves a list of cat breed names from an API using HttpClient and
 * Gson in Java.
 * 
 * API: https://thecatapi.com/
 */
public class CatBreedService implements PropertiesLoader {
    private String apiURL;

    /**
     * The function `getBreedNames` retrieves a list of breed names from an API using HttpClient and Gson
     * in Java.
     * 
     * @return This method returns a list of breed names as strings.
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