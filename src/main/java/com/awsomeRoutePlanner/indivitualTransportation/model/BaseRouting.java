package com.awsomeRoutePlanner.indivitualTransportation.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaseRouting {
    private List<String> errors = new ArrayList<>();
    private JsonNode route;

    public BaseRouting addError(String errorMessage){
        errors.add(errorMessage);
        return this;
    }
}
