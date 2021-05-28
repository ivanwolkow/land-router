package com.example.landrouter.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class LandGraphService implements InitializingBean {

    private Graph<String, DefaultEdge> landGraph;

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream is = LandGraphService.class.getClassLoader().getResourceAsStream("countries.json");

        List<CountryItem> countries = new ObjectMapper().readerFor(new TypeReference<List<CountryItem>>() {})
                .readValue(is);

        DefaultUndirectedGraph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        StreamEx.of(countries)
                .map(CountryItem::getCca3)
                .forEach(graph::addVertex);

        StreamEx.of(countries)
                .mapToEntry(CountryItem::getCca3, CountryItem::getBorders)
                .flatMapValues(Collection::stream)
                .forKeyValue(graph::addEdge);

        this.landGraph = graph;
    }

    public Graph<String, DefaultEdge> getLandGraph() {
        return landGraph;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CountryItem {
        private String cca3;
        private List<String> borders;
    }

}
