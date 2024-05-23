package com.awsomeRoutePlanner.indivitualTransportation.client;

import com.awsomeRoutePlanner.indivitualTransportation.model.LatLon;
import com.squareup.okhttp.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class EvRoutingClient {

    // /routing/{versionNumber}/calculateLongDistanceEVRoute/{locations}/{contentType}
    private final String baseUrl = "https://api.tomtom.com/routing/1/calculateLongDistanceEVRoute";

    public String getRoute(LatLon origin, LatLon destination){
        String responseString = "";
        OkHttpClient client = new OkHttpClient();
        String json = readRequest();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        String url = getFullUrl(toLocationString(origin, destination));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            responseString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return responseString;
    }

    private String toLocationString(LatLon origin, LatLon destination) {
        // return value pattern: lat1,lon1:lat2,lon2 , then special chars are escaped too
        return "%s,%s:%s,%s"
                .formatted(origin.getLat(), origin.getLon(), destination.getLat(), destination.getLon())
                .replace(",", "%2C")
                .replace(":", "%3A");
    }

    private String readRequest() {
        ClassLoader classLoader = EvRoutingClient.class.getClassLoader();
        try {
            return Files.readString(Paths.get(classLoader.getResource("ev-request-body.json").toURI()));
        } catch (Exception e) {
            return "";
        }
    }

    private String getFullUrl(String location) {
        // location example: "50.95421%2C6.90852%3A52.50825%2C13.45686"
        String url = baseUrl + "/" + location;
        url += "/json?vehicleEngineType=electric&constantSpeedConsumptionInkWhPerHundredkm=50.0%2C6.5%3A100.0%2C8.5&currentChargeInkWh=10&maxChargeInkWh=40&minChargeAtDestinationInkWh=5.2&minChargeAtChargingStopsInkWh=1.5";
        url += "&key=lgIf9FRTLxNVlqDxYi90ZiCCE81R5jYE";
        return url;
    }

}
