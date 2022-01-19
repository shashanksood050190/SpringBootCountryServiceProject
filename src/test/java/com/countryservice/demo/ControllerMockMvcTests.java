package com.countryservice.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.countryservice.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
public class ControllerMockMvcTests {

	@Autowired
	MockMvc mockMvc;

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	List<Country> mycountries;
	Country country;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}

	@Test
	@Order(1)
	public void test_getAllCountries() throws Exception {
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "USA", "Washington"));
		when(countryService.getAllCountries()).thenReturn(mycountries);
		this.mockMvc.perform(get("/getCountries")).andExpect(status().isFound()).andDo(print());
	}

	@Test
	@Order(2)
	public void test_getCountryById() {
		
		try {
			country = new Country(2, "USA", "Washington");
			int countryid = 2;

			when(countryService.getCountryById(countryid)).thenReturn(country);
			
			this.mockMvc.perform(get("/getCountries/{id}",countryid))
			//.andExpect(status().isFound())
			.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
			.andDo(print());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() {
		
		try {
			country = new Country(2, "USA", "Washington");
			String countryName = "USA";

			when(countryService.getCountryByName(countryName)).thenReturn(country);
			
			this.mockMvc.perform(get("/getCountries/countryName").param("name", "USA"))
		    //.andExpect(status().isFound())
			.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
			.andDo(print());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		
		try {
			country = new Country(3, "Germany", "Berlin");

			when(countryService.addCountry(country)).thenReturn(country);
			
			ObjectMapper mapper=new ObjectMapper();
			String jsonBody=mapper.writeValueAsString(country);
			
			this.mockMvc.perform(post("/addCountry")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					)
		    .andExpect(status().isCreated())
			.andDo(print());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		
		try {
			country = new Country(3, "Japan", "Tokyo");
			int countryId=3;

			when(countryService.getCountryById(countryId)).thenReturn(country);
			when(countryService.updateCountry(country)).thenReturn(country);
			
			ObjectMapper mapper=new ObjectMapper();
			String jsonBody=mapper.writeValueAsString(country);
			
			this.mockMvc.perform(put("/updateCountry/{id}",countryId)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					)
		    .andExpect(status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo"))
			.andDo(print());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		
		try {
			country = new Country(3, "Japan", "Tokyo");
			int countryId=3;

			when(countryService.getCountryById(countryId)).thenReturn(country);
			
			this.mockMvc.perform(delete("/deleteCountry/{id}",countryId))
		    .andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
