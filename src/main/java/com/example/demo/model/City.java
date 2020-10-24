package com.example.demo.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class represents a city along with connected cities.
 */
public class City {
	
	private String name;
	
	private Set<City> connectedCities = new HashSet<>();
	
	private City(){
		
	}
	
	private City(String name){
		Objects.requireNonNull(name);
		this.name = name;
	}
	
	public static City build(String name){
		return new City(name);
	}
	
	public City addConnectedCities(City city){
		connectedCities.add(city);
		return this;
	}
	
	public Set<City> getConnectedCities(){
		return connectedCities;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "Name: " + name +
			   "Connected Cities: " + connectedCities.stream().map(City::getName).collect(Collectors.joining(","));
	}
}
