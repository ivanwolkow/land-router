package com.example.landrouter.config;

import com.example.landrouter.service.algorithms.custom.CustomGraph;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Configuration
@Slf4j
public class LandGraphConfiguration {

    @Bean
    @ConditionalOnProperty(name = "routing-algorithm", havingValue = "jgrapht-dijkstra")
    public Graph<String, DefaultEdge> jGraph() {
        log.info("Initializing jGraph graph...");

        List<CountryItem> countries = loadCountryData();

        DefaultUndirectedGraph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        StreamEx.of(countries)
                .map(CountryItem::getCca3)
                .forEach(graph::addVertex);

        StreamEx.of(countries)
                .mapToEntry(CountryItem::getCca3, CountryItem::getBorders)
                .flatMapValues(Collection::stream)
                .forKeyValue(graph::addEdge);

        return graph;
    }

    @Bean
    @ConditionalOnProperty(name = "routing-algorithm", havingValue = "custom-dijkstra")
    public CustomGraph<String> customGraph() {
        log.info("Initializing custom graph...");

        List<CountryItem> countries = loadCountryData();

        var graph = new CustomGraph<String>();

        StreamEx.of(countries)
                .map(CountryItem::getCca3)
                .forEach(graph::addNode);

        StreamEx.of(countries)
                .mapToEntry(CountryItem::getCca3, CountryItem::getBorders)
                .flatMapValues(Collection::stream)
                .forKeyValue(graph::addEdge);

        return graph;
    }

    @SneakyThrows
    private List<CountryItem> loadCountryData() {
        InputStream is = LandGraphConfiguration.class.getClassLoader().getResourceAsStream("countries.json");

        List<CountryItem> countries = new ObjectMapper().readerFor(new TypeReference<List<CountryItem>>() {})
                .readValue(is);

        log.info("Loaded {} countries", countries.size());
        return countries;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CountryItem {
        private String cca3;
        private List<String> borders;
    }
}
