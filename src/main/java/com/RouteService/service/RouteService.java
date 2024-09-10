package com.RouteService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RouteService.exception.RouteNotFoundException;
import com.RouteService.model.Route;
import com.RouteService.repository.RouteRepository;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public Route addRoute(Route route) {
        // Assuming validation is handled elsewhere
        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        if (routes.isEmpty()) {
            throw new RouteNotFoundException("No routes found");
        }
        return routes;
    }

    public void deleteRoute(int routeId) {
        // Check if route exists before deleting
        if (!routeRepository.existsById(routeId)) {
            throw new RouteNotFoundException("Route with ID " + routeId + " not found");
        }
        routeRepository.deleteById(routeId);
    }

    public Route modifyRoute(int routeId, Route modifiedRoute) {
        Optional<Route> optRoute = routeRepository.findById(routeId);

        if (optRoute.isPresent()) {
            Route existingRoute = optRoute.get();
            existingRoute.setSource(modifiedRoute.getSource());
            existingRoute.setDestination(modifiedRoute.getDestination());
            existingRoute.setDistance(modifiedRoute.getDistance());
            existingRoute.setDuration(modifiedRoute.getDuration());

            return routeRepository.save(existingRoute);
        } else {
            throw new RouteNotFoundException("Route with ID " + routeId + " not found");
        }
    }

    public Route getRouteById(int routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException("Route with ID " + routeId + " not found"));
    }
}
