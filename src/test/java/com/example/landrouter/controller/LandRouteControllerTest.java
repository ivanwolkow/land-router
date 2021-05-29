package com.example.landrouter.controller;

import com.example.landrouter.LandRouterApplication;
import com.example.landrouter.service.algorithms.LandRoutingAlgorithm;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = {LandRouterApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class LandRouteControllerTest {

    @Autowired
    private LandRoutingAlgorithm algorithm;

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void landRoutingHappyPathCzeIta() {
        mockMvc.perform(MockMvcRequestBuilders.get("/routing/CZE/ITA"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'route':['CZE','AUT','ITA']}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @SneakyThrows
    void landRoutingHappyPathPrtChn() {
        mockMvc.perform(MockMvcRequestBuilders.get("/routing/PRT/CHN"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'route':['PRT','ESP','FRA','DEU','POL','RUS','CHN']}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @SneakyThrows
    void landRoutingSourceIsDestination() {
        mockMvc.perform(MockMvcRequestBuilders.get("/routing/CZE/CZE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'route':['CZE']}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @SneakyThrows
    void landRoutingBadLandName() {
        mockMvc.perform(MockMvcRequestBuilders.get("/routing/CZE/XXX"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @SneakyThrows
    void landRoutingDestinationUnreachable() {
        mockMvc.perform(MockMvcRequestBuilders.get("/routing/CZE/USA"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

}