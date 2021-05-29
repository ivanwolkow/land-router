package com.example.landrouter.service.algorithms;

import java.util.List;

public interface LandRoutingAlgorithm {

    List<String> findShortestDistance(String src, String dst);
}
