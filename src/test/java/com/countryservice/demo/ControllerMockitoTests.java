package com.countryservice.demo;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

@SpringBootTest(classes = {ControllerMockitoTests.class})
public class ControllerMockitoTests {

	@Mock
	CountryService countryService;
	
	@InjectMocks
	CountryController countryController;
	
	List<Country> mycountries;
	Country country;
	
	@Test
	@Order(1)
	public void test_getAllCountries()
	{
		mycountries=new ArrayList<Country>();
		mycountries.add(new Country(1,"India","Delhi"));
		mycountries.add(new Country(2,"USA","Washington"));
		countryController.getCountries();
		when(countryService.getAllCountries()).thenReturn(mycountries);
		ResponseEntity<List<Country>> res=countryController.getCountries();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
	}
}
