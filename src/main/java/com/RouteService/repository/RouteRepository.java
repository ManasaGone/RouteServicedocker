package com.RouteService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RouteService.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

}
