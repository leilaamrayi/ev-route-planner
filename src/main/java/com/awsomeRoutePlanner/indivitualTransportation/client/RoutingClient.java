package com.awsomeRoutePlanner.indivitualTransportation.client;

import com.awsomeRoutePlanner.indivitualTransportation.model.LatLon;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.stereotype.Component;

@Component
public class RoutingClient {

    private final String baseUrl = "https://api.geoapify.com/v1";
    public String getRoute(LatLon origin, LatLon destination, String mode) {
        String waypoints = createWayPointString(origin, destination);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl+"/routing?waypoints="+waypoints+"&mode="+mode+"&apiKey=7fbfdcf8405d4b04a7dee8c44dff6620")
                .method("GET", null)
                .build();
        String responseBody = null;
        try {
            System.out.println("Sending request to geo API: "+request.url());
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            System.out.println("Response from geo API: "+responseBody);
        } catch (Exception e) {}
        return responseBody;
    }


    /**
     * example return value: "57.732386,11.923801|57.7235603,11.8910587"
     */
    public String createWayPointString(LatLon origin, LatLon destination) {
        String wayPointString = String.format("%s,%s|%s,%s",
                origin.getLat(), origin.getLon(),
                destination.getLat(), destination.getLon());
        return wayPointString;
    }

    public LatLon getLatLon(String originAddress) {
//        String url  = "https://api.geoapify.com/v1/geocode/search?text=toleredsgatan%2020a&format=json&apiKey=7fbfdcf8405d4b04a7dee8c44dff6620";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl+"/geocode/search?text="+originAddress+"&format=json&apiKey=7fbfdcf8405d4b04a7dee8c44dff6620")
                .method("GET", null)
                .build();

        String responseBody = null;

        try {
            System.out.println("Sending request to geo API: "+request.url());
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            System.out.println("Response from geo API: "+responseBody);
            JsonNode node = new ObjectMapper().readTree(responseBody);
            node = node.get("results").get(0);
            LatLon latLon = new LatLon()
                    .setLat(String.valueOf(node.get("lat").doubleValue()))
                    .setLon(String.valueOf(node.get("lon").doubleValue()));
            return latLon;
        } catch (Exception e) {
            return null;
        }

    }

}
