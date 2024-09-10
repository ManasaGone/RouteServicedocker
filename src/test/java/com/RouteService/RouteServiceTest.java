package com.RouteService;

import com.RouteService.exception.RouteNotFoundException;
import com.RouteService.model.Route;
import com.RouteService.repository.RouteRepository;
import com.RouteService.service.RouteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRoute() {
        Route route = new Route(1, "Source", "Destination", 100.0f, 2.0f);
        when(routeRepository.save(route)).thenReturn(route);

        Route savedRoute = routeService.addRoute(route);
        assertNotNull(savedRoute);
        assertEquals(route.getRouteId(), savedRoute.getRouteId());
    }

    @Test
    void testGetAllRoutes() {
        Route route = new Route(1, "Source", "Destination", 100.0f, 2.0f);
        when(routeRepository.findAll()).thenReturn(Collections.singletonList(route));

        assertFalse(routeService.getAllRoutes().isEmpty());
    }

    @Test
    void testDeleteRouteWhenExists() {
        int routeId = 1;
        when(routeRepository.existsById(routeId)).thenReturn(true);

        assertDoesNotThrow(() -> routeService.deleteRoute(routeId));
        verify(routeRepository, times(1)).deleteById(routeId);
    }

    @Test
    void testDeleteRouteWhenNotExists() {
        int routeId = 1;
        when(routeRepository.existsById(routeId)).thenReturn(false);

        RouteNotFoundException thrown = assertThrows(RouteNotFoundException.class, () -> {
            routeService.deleteRoute(routeId);
        });

        assertEquals("Route with ID " + routeId + " not found", thrown.getMessage());
    }

    @Test
    void testModifyRouteWhenExists() {
        int routeId = 1;
        Route existingRoute = new Route(routeId, "OldSource", "OldDestination", 50.0f, 1.0f);
        Route modifiedRoute = new Route(routeId, "NewSource", "NewDestination", 100.0f, 2.0f);

        when(routeRepository.findById(routeId)).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        Route updatedRoute = routeService.modifyRoute(routeId, modifiedRoute);

        assertNotNull(updatedRoute);
        assertEquals("NewSource", updatedRoute.getSource());
        assertEquals("NewDestination", updatedRoute.getDestination());
    }

    @Test
    void testModifyRouteWhenNotExists() {
        int routeId = 1;
        Route modifiedRoute = new Route(routeId, "NewSource", "NewDestination", 100.0f, 2.0f);

        when(routeRepository.findById(routeId)).thenReturn(Optional.empty());

        RouteNotFoundException thrown = assertThrows(RouteNotFoundException.class, () -> {
            routeService.modifyRoute(routeId, modifiedRoute);
        });

        assertEquals("Route with ID " + routeId + " not found", thrown.getMessage());
    }

    @Test
    void testGetRouteByIdWhenExists() {
        int routeId = 1;
        Route route = new Route(routeId, "Source", "Destination", 100.0f, 2.0f);

        when(routeRepository.findById(routeId)).thenReturn(Optional.of(route));

        Route fetchedRoute = routeService.getRouteById(routeId);
        assertNotNull(fetchedRoute);
        assertEquals(routeId, fetchedRoute.getRouteId());
    }

    @Test
    void testGetRouteByIdWhenNotExists() {
        int routeId = 1;

        when(routeRepository.findById(routeId)).thenReturn(Optional.empty());

        RouteNotFoundException thrown = assertThrows(RouteNotFoundException.class, () -> {
            routeService.getRouteById(routeId);
        });

        assertEquals("Route with ID " + routeId + " not found", thrown.getMessage());
    }
}
