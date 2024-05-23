package com.awsomeRoutePlanner.indivitualTransportation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LatLon {
    private String lat;
    private String lon;

    public String toCommaSeparatedString(){
        return lat + "," + lon;
    }

}
