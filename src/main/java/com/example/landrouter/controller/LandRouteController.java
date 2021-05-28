package com.example.landrouter.controller;

import com.example.landrouter.model.LandRouteResponse;
import com.example.landrouter.service.LandRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LandRouteController {

    private final LandRouteService landRouteService;

    @GetMapping("/routing/{origin}/{destination}")
    public LandRouteResponse getRouteBetweenLands(@PathVariable("origin") String origin,
                                                  @PathVariable("destination") String destination) {
        return new LandRouteResponse(landRouteService.shortestPath(origin, destination));
    }
}
