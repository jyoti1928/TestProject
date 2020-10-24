package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class DemoApplicationTests {

	 @Autowired
	 private TestRestTemplate restTemplate;
	 
	 
	 @Test
	 public void testDirectConnectedCities() {

		 Map<String, String> params = new HashMap<>();
	     params.put("origin", "Boston");
	     params.put("destination", "Newark");

	     String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
	     Assert.assertEquals("yes", response);
	 }
	 
	 @Test
	 public void testIndirectConnectedCities() {

		 Map<String, String> params = new HashMap<>();
	     params.put("origin", "Boston");
	     params.put("destination", "Philadelphia");

	     String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
	     Assert.assertEquals("yes", response);
	 }

	 @Test
	 public void testNotConnectedCities() {
		 
		 Map<String, String> params = new HashMap<>();
	     params.put("origin", "Philadelphia");
	     params.put("destination", "Albany");

	     String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
	     Assert.assertEquals("no", response);
	 }
	 
	 @Test
	 public void testNullCities() {
		 
		 Map<String, String> params = new HashMap<>();
	     params.put("origin", "null");
	     params.put("destination", "null");

	     String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
	     Assert.assertEquals("no", response);
	 }

}
