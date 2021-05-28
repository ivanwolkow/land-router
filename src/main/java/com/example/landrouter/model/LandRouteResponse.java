package com.example.landrouter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LandRouteResponse {

    private List<String> route;
}
