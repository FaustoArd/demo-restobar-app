package com.lord.arbam;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RestoTableIntegrationControllersTest {

	@Autowired
	private MockMvc mockMvc;

	private MvcResult mvcResult;

	private String jwtToken;

	@Test
	@Order(1)
	void registerNewUser() throws Exception {
		this.mockMvc.perform(post("http://localhost:8080/api/v1/arbam/authentication/register").content(
				"{\"name\":\"Mario\",\"lastname\":\"Rojas\",\"username\":\"mar\",\"email\":\"mar@gmail.com\",\"password\":\"1234\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.name", is("Mario")))
				.andExpect(jsonPath("$.lastname", is("Rojas"))).andExpect(jsonPath("$.username", is("mar")))
				.andExpect(jsonPath("$.password", is(notNullValue())));
	}

	@Test
	@Order(2)
	void loginUser() throws Exception {
		this.mvcResult = this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/authentication/login")
						.content("{\"username\":\"mar\",\"password\":\"1234\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.username", is("mar"))).andExpect(jsonPath("$.jwtToken", is(notNullValue())))
				.andReturn();

		String[] response = this.mvcResult.getResponse().getContentAsString().split("\"");
		for (String str : response) {
			if (str.length() > 200) {
				this.jwtToken = str;
			}
		}
	}

	@Test
	@Order(3)
	void whenCreateNewRestoTable_MustReturnNewRestoTableDto() throws Exception {

		this.mockMvc.perform(post("http://localhost:8080/api/v1/arbam/resto_tables/new_table")
				.content("{\"tableNumber\":15,\"employeeId\":1}").header("Authorization", "Bearer " + this.jwtToken)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(notNullValue()))).andExpect(jsonPath("$.employeeName", is("Carla")))
				.andExpect(jsonPath("$.open", is(true)));
	}
	

}
