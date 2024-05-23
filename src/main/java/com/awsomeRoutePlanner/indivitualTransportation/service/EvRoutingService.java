package com.awsomeRoutePlanner.indivitualTransportation.service;

import com.awsomeRoutePlanner.indivitualTransportation.client.EvRoutingClient;
import com.awsomeRoutePlanner.indivitualTransportation.client.RoutingClient;
import com.awsomeRoutePlanner.indivitualTransportation.model.EvRouting;
import com.awsomeRoutePlanner.indivitualTransportation.model.LatLon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EvRoutingService {
    @Autowired
    EvRoutingClient evRoutingClient;
    @Autowired
    private RoutingClient routingClient;

    public EvRouting getRoute(String originAddress, String destinationAddress){

        EvRouting routing = new EvRouting();

        // get latlon by street address
        LatLon origin = routingClient.getLatLon(originAddress);
        LatLon destination = routingClient.getLatLon(destinationAddress);
        if (origin == null || destination == null){
            routing.setErrors(List.of("Could not fetch address details."));
            return routing;
        }

        // get routing info
        String route = evRoutingClient.getRoute(origin, destination);
        if (route == null){
            routing.addError("Could not get EV charging points information from third party service.");
        } else {
            JsonNode node = null;
            try {
                node = new ObjectMapper().readTree(route);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            routing.setRoute(node);
        }

        // prepare service response
        if (routing.getRoute() == null){
            routing.setErrors(List.of("Failed to process third party API response."));
        }

        return routing;
    }

}
