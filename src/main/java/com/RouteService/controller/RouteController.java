package com.RouteService.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RouteService.model.Route;
import com.RouteService.service.RouteService;

@RestController
@RequestMapping("/route")
@CrossOrigin("*")  
public class RouteController {

	@Autowired
	private RouteService routeService;
	
	@PostMapping("/Addroute")
	public Route addRoute(@RequestBody Route route) {
		return routeService.addRoute(route);
	}
	
	@GetMapping("/ViewAllRoutes")
	public List<Route> getAllRoutes(){
		return routeService.getAllRoutes();
	}
	
	@GetMapping("/ViewRouteById/{routeId}")
	public Route getRouteById(@PathVariable int routeId) {
		return routeService.getRouteById(routeId);
	}
	
	@DeleteMapping("/DeleteRoute/{routeId}")
	public void deleteRoute(@PathVariable int routeId) {
		routeService.deleteRoute(routeId);
	}
	
	@PutMapping("/ModifyRoute/{routeId}")
	public Route modifyRoute(@PathVariable int routeId,@RequestBody Route route) {
		return routeService.modifyRoute(routeId, route);
	}
	
}
