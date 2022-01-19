package com.countryservice.demo;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {

	@Test
	@Order(1)
	void getAllCountriesIntegrationTest() throws JSONException {
		String expected = "[\n{\n\"id\": 1,\n\"countryName\": \"India\",\n\"countryCapital\": \"Delhi\"\n},\n{\n\"id\": 2,\n\"countryName\": \"USA\",\n\"countryCapital\": \"Washington\"\n}\n]";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getCountries", String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@Order(2)
	void getCountryByIDIntegrationTest() throws JSONException {

		String expected = "{\n\"id\": 1,\n\"countryName\": \"India\",\n\"countryCapital\": \"Delhi\"\n}";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getCountries/1",
				String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@Order(3)
	void getCountryByNameIntegrationTest() throws JSONException {

		String expected = "{\n\"id\": 1,\n\"countryName\": \"India\",\n\"countryCapital\": \"Delhi\"\n}";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:8080/getCountries/countryname?name=India", String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@Order(4)
	void addCountryIntegrationTest() throws JSONException {
		Country country = new Country(3, "Germany", "Berlin");
		String expected = "{\r\n" + "    \"countryName\":\"Germany\",\r\n" + "    \"countryCapital\":\"Berlin\"\r\n"
				+ "}";

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/addCountry", request,
				String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	@Order(5)
	void updateCountryIntegrationTest() throws JSONException {
		Country country = new Country(3, "Japan", "Tokyo");
		String expected = "{\r\n" + "    \"id\": 3,\r\n" + "    \"countryName\": \"Japan\",\r\n"
				+ "    \"countryCapital\": \"Tokyo\"\r\n" + "}";

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/updateCountry/3", HttpMethod.PUT,
				request, String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(6)
	void deleteCountryIntegrationTest() throws JSONException {
		Country country = new Country(3, "Japan", "Tokyo");
		String expected = "{\r\n" + "    \"id\": 3,\r\n" + "    \"countryName\": \"Japan\",\r\n"
				+ "    \"countryCapital\": \"Tokyo\"\r\n" + "}";

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/deleteCountry/3", HttpMethod.DELETE,
				request, String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
}
