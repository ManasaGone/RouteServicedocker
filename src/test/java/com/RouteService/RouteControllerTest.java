package com.RouteService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.RouteService.controller.RouteController;
import com.RouteService.model.Route;
import com.RouteService.service.RouteService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RouteControllerTest {

    @Mock
    private RouteService routeService;

    @InjectMocks
    private RouteController routeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(routeController).build();
    }

    @Test
    void testAddRoute() throws Exception {
        Route route = new Route();
        route.setRouteId(1); // In practice, this is set by the database.
        route.setSource("Source City");
        route.setDestination("Destination City");
        route.setDistance(100.5f);
        route.setDuration(2.5f);

        doReturn(route).when(routeService).addRoute(any(Route.class));

        mockMvc.perform(post("/route/Addroute")
                .contentType("application/json")
                .content("{ \"source\": \"Source City\", \"destination\": \"Destination City\", \"distance\": 100.5, \"duration\": 2.5 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeId").value(1))
                .andExpect(jsonPath("$.source").value("Source City"))
                .andExpect(jsonPath("$.destination").value("Destination City"))
                .andExpect(jsonPath("$.distance").value(100.5))
                .andExpect(jsonPath("$.duration").value(2.5));
    }

    @Test
    void testGetAllRoutes() throws Exception {
        List<Route> routes = new ArrayList<>();
        Route route = new Route();
        route.setRouteId(1);
        route.setSource("Source City");
        route.setDestination("Destination City");
        route.setDistance(100.5f);
        route.setDuration(2.5f);
        routes.add(route);

        doReturn(routes).when(routeService).getAllRoutes();

        mockMvc.perform(get("/route/ViewAllRoutes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].routeId").value(1))
                .andExpect(jsonPath("$[0].source").value("Source City"))
                .andExpect(jsonPath("$[0].destination").value("Destination City"))
                .andExpect(jsonPath("$[0].distance").value(100.5))
                .andExpect(jsonPath("$[0].duration").value(2.5));
    }

    @Test
    void testGetRouteById() throws Exception {
        Route route = new Route();
        route.setRouteId(1);
        route.setSource("Source City");
        route.setDestination("Destination City");
        route.setDistance(100.5f);
        route.setDuration(2.5f);

        doReturn(route).when(routeService).getRouteById(anyInt());

        mockMvc.perform(get("/route/ViewRouteById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeId").value(1))
                .andExpect(jsonPath("$.source").value("Source City"))
                .andExpect(jsonPath("$.destination").value("Destination City"))
                .andExpect(jsonPath("$.distance").value(100.5))
                .andExpect(jsonPath("$.duration").value(2.5));
    }

   
    @Test
    void testModifyRoute() throws Exception {
        Route route = new Route();
        route.setRouteId(1);
        route.setSource("Updated Source");
        route.setDestination("Updated Destination");
        route.setDistance(200.0f);
        route.setDuration(3.0f);

        doReturn(route).when(routeService).modifyRoute(anyInt(), any(Route.class));

        mockMvc.perform(put("/route/ModifyRoute/1")
                .contentType("application/json")
                .content("{ \"source\": \"Updated Source\", \"destination\": \"Updated Destination\", \"distance\": 200.0, \"duration\": 3.0 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeId").value(1))
                .andExpect(jsonPath("$.source").value("Updated Source"))
                .andExpect(jsonPath("$.destination").value("Updated Destination"))
                .andExpect(jsonPath("$.distance").value(200.0))
                .andExpect(jsonPath("$.duration").value(3.0));
    }
}
