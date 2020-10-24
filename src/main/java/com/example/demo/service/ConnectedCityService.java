package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.example.demo.model.City;


@Service
public class ConnectedCityService {
	
	@Value("${data.file:classpath:city.txt}")
	private String CITIES;
	
	@Autowired
	private ResourceLoader resourceLoader;
	private static final Log LOG = LogFactory.getLog(ConnectedCityService.class);
	
	public Map<String, City> cityMap = new HashMap<>();
	
	public Map<String,City> getCityMap(){
		return cityMap;
	}

	/**
	 * This method checks if origin city is connected to destination city directly or indirectly
	 * @param origin
	 * @param destination
	 * @return
	 * @throws IOException
	 */
	public String checkIfCitiesAreConnected(String origin, String destination) throws IOException {
		
		LOG.info("Origin: " + origin + ", destination: " + destination);
		
		List<City> indirectConnectedCities = new ArrayList<>();
		
		Set<City> directConnectedCities = getCityMap().get(origin.toUpperCase()).getConnectedCities();
		
		directConnectedCities.forEach(city -> city.getConnectedCities().forEach(indirectConnectedCities::add));
		
		LOG.info(" Direct Connected cities: "+ directConnectedCities.stream().map(City::getName).collect(Collectors.joining(",")));
		LOG.info(" Indirect Connected cities: "+ indirectConnectedCities.stream().distinct().map(City::getName).collect(Collectors.joining(",")));

		if( directConnectedCities.stream().anyMatch(city -> city.getName().equals(destination.toUpperCase())) || 
				indirectConnectedCities.stream().anyMatch(city -> city.getName().equals(destination.toUpperCase())))
		{
			return "yes";
		}else{
			return "no";
		}
		
	}
	
	@PostConstruct
	public void readFile() throws IOException{
		
			LOG.info("Reading City Data from City.txt");
			
			Resource resource = resourceLoader.getResource(CITIES);
			
	        InputStream inputStream = resource.getInputStream(); 
	        Scanner scanner = new Scanner(inputStream);
	        
	        String city1, city2, line;
	        City origin ,dest;
	        String[] split;
	        
			while(scanner.hasNext()){
				
				 line = scanner.nextLine();
				 
				 split = line.split(",");
				
				 city1 = split[0].trim().toUpperCase();
				 city2 = split[1].trim().toUpperCase();
				
				 origin = cityMap.getOrDefault(city1, City.build(city1));
				 dest = cityMap.getOrDefault(city2, City.build(city2));
				
				 origin.addConnectedCities(dest);
				 dest.addConnectedCities(origin);
				
				 cityMap.put(origin.getName(), origin);
				 cityMap.put(dest.getName(), dest);
			}
			
			scanner.close();
	}

}
