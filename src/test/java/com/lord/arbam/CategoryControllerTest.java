package com.lord.arbam;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
public class CategoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	private String jwtToken;

	private MvcResult mvcResult;
	
	@Test
	@Order(1)
	void registerNewUser() throws Exception {
		mockMvc.perform(post("http://localhost:8080//api/v1/arbam/authentication/register").content(
				"{\"name\":\"Carlos\",\"lastname\":\"Marino\",\"username\":\"car\",\"email\":\"car@mail.com\"," + "\"password\":\"1234\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("Carlos")))
				.andExpect(jsonPath("$.lastname", is("Marino")))
				.andExpect(jsonPath("$.username", is("car")))
				.andExpect(jsonPath("$.email", is("car@mail.com")))
				.andExpect(jsonPath("$.password", is(notNullValue())));
	}

	// Login user and extract token
	@Test
	@Order(2)
	void loginUserAndExtractToken() throws Exception {
		this.mvcResult = mockMvc
				.perform(post("http://localhost:8080//api/v1/arbam/authentication/login")
						.content("{\"username\":\"car\",\"password\":\"1234\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.jwtToken", is(notNullValue()))).andReturn();

		String[] splitResponse = this.mvcResult.getResponse().getContentAsString().split("\"");
		for(String str:splitResponse) {
			if(str.length()>200) {
				this.jwtToken = str;
			}
		}
		
	}
	
	@Test
	@Order(3)
	void WhenCreateNewCategory_MustReturnProductCategoryDto()throws Exception{
		
		mockMvc.perform(post("http://localhost:8080//api/v1/arbam/categories/")
				.content("{\"categoryName\":\"Pizza\"}").header("Authorization", "Bearer "+ this.jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.categoryName", is("Pizza")));
	}
	
	

}
