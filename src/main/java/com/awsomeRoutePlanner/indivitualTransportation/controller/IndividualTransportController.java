package com.awsomeRoutePlanner.indivitualTransportation.controller;

import com.awsomeRoutePlanner.indivitualTransportation.model.EvRouting;
import com.awsomeRoutePlanner.indivitualTransportation.service.EvRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndividualTransportController {


    @Autowired
    EvRoutingService evRoutingService;

    @GetMapping("/route/ev")
    public ResponseEntity<EvRouting> getEvChargingInfo(@RequestParam String origin, @RequestParam String destination) {
        return ResponseEntity.ok(evRoutingService.getRoute( origin, destination));
    }

}
