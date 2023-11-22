package com.example.springapp;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.springapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.json.simple.parser.ParseException;

@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringappApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

    private String generatedToken;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Test
	@Order(1)
	void testRegisterUser() throws Exception {
		String requestBody = "{\"email\": \"abcd@gmail.com\", \"password\": \"abc\", \"username\": \"abcd\", \"mobileNumber\": \"9876543210\", \"userRole\": \"CUSTOMER\"}";
			mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$").value(true))
					.andReturn();
	}

	@Test
	@Order(2)
	void testRegisterAdmin() throws Exception {
		String requestBody = "{\"email\": \"abc@gmail.com\", \"password\": \"abc\", \"username\": \"abc\",\"mobileNumber\": \"9876543210\",\"userRole\": \"ADMIN\"}";

			mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$").value(true))
					.andReturn();
	}

	@Test
	@Order(3)
	public void testLoginUser() throws Exception {
		testRegisterUser();
			String requestBody = "{\"email\": \"abcd@gmail.com\", \"password\": \"abc\"}";

			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();

			String responseString = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> responseMap = mapper.readValue(responseString, Map.class);

            String token = responseMap.get("token");
            generatedToken = token;

			assertNotNull(token);
	}

	
    @Test
    @Order(4)
    public void testLoginAdmin() throws Exception {
        testRegisterAdmin();
        String requestBody = "{\"email\": \"abc@gmail.com\",\"password\": \"abc\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();

			String responseString = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> responseMap = mapper.readValue(responseString, Map.class);

            String token = responseMap.get("token");
            generatedToken = token;

			assertNotNull(token);
	
    }

	@Test
	@Order(5)
	public void testAddPlan() throws Exception {
		testLoginAdmin();
		String requestBody = "{\"planType\": \"PREPAID\",\"planName\": \"plan1\", \"planValidity\": \"1 month\", \"planDetails\": \"plan1\", \"planPrice\": 19.99}";
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/addplan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(6)
	public void testGetAllPlans() throws Exception {
		testLoginAdmin();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/getallplan")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(7)
	public void testUpdatePlan() throws Exception{
		testLoginAdmin();
		String requestBody = "{\"planType\": \"PREPAID\",\"planName\": \"plan1\", \"planValidity\": \"1 month\", \"planDetails\": \"plan1\", \"planPrice\": 29.99}";
		mockMvc.perform(MockMvcRequestBuilders.put("/admin/editplan/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(8)
	public void testAddAddOn() throws Exception {
		testLoginAdmin();
		String requestBody = "{\"addonName\": \"addon1\",\"addonPrice\": 19.99, \"addOnDetails\": \"addon1\",\"addonValidity\": \"1 month\" }";
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/addaddon")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}


	@Test
	@Order(9)
	public void testGetAllAddOns() throws Exception {
		testLoginAdmin();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/getaddon")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(10)
	public void testUpdateAddOn() throws Exception{
		testLoginAdmin();
		String requestBody = "{\"addonName\": \"addon1\",\"addonPrice\": 29.99, \"addOnDetails\": \"addon1\",\"addonValidity\": \"1 month\" }";
		mockMvc.perform(MockMvcRequestBuilders.put("/admin/editaddon/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(11)
	public void testGetPaymentHistory() throws Exception {
		testLoginAdmin();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/getpaymenthistory")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(12)
	public void testAddRecharge() throws Exception {
		testLoginUser();
		String requestBody = "{"
		+ "\"mobile\": \"9876543210\","
		+ "\"rechargePrice\": 25.99,"
		+ "\"status\": \"Success\","
		+ "\"date\": \"2023-11-21T10:20:03.692Z\","
		+ "\"plan\": {"
		+ "  \"planId\": 1,"
		+ "  \"planType\": \"Prepaid\","
		+ "  \"planName\": \"Basic Plan\","
		+ "  \"planValidity\": \"30 Days\","
		+ "  \"planDetails\": \"Unlimited calls and texts, 2GB data\","
		+ "  \"planPrice\": 19.99"
		+ "},"
		+ "\"user\": {"
		+ "  \"userId\": 1,"
		+ "  \"email\": \"abcd@gmail.com\","
		+ "  \"password\": \"abc\","
		+ "  \"username\": \"abcd\","
		+ "  \"mobileNumber\": \"9876543210\","
		+ "  \"userRole\": \"CUSTOMER\""
		+ "}"
		+ "}";		
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/addrecharge")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}


	@Test
	@Order(13)
	public void testGetAllPlansUser() throws Exception {
		testLoginUser();
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/getallplan")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(14)
	public void testGetAllAddOnsUser() throws Exception {
		testLoginUser();
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/getalladdons")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(15)
	public void testMakePayment() throws Exception {
		testLoginUser();
		String requestBody = "{"
			+ "\"status\": \"Success\","
			+ "\"totalAmount\": 25.99,"
			+ "\"paymentDate\": \"2023-11-21T10:20:03.692Z\","
			+ "\"modeOfPayment\": \"Credit Card\","
			+ "\"userId\": 1,"
			+ "\"recharge\": {"
			+ "  \"rechargeId\": 1,"
			+ "  \"mobile\": \"9876543210\","
			+ "  \"rechargePrice\": 25.99,"
			+ "  \"status\": \"Success\","
			+ "  \"date\": \"2023-11-21T10:20:03.692Z\","
			+ "  \"plan\": {"
			+ "    \"planId\": 1,"
			+ "    \"planType\": \"Prepaid\","
			+ "    \"planName\": \"Basic Plan\","
			+ "    \"planValidity\": \"30 Days\","
			+ "    \"planDetails\": \"Unlimited calls and texts, 2GB data\","
			+ "    \"planPrice\": 19.99"
			+ "  }"
			+ "}"
			+ "}";
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/make-payment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.header("Authorization", "Bearer " + generatedToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

}
