package com.pache.igschedule.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pache.igschedule.config.TestConfiguration;
import com.pache.igscheduleuser.controller.UserCommand;
import com.pache.igscheduleuser.controller.resourcesuporte.UserList;
import com.pache.igscheduleuser.entity.User;
import com.pache.igscheduleuser.repository.UserRepository;

/**
 * Created by lpache on 7/18/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class UserControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserRepository repository;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		repository.save(new User(1l, "User 1", "Abreu", "123456", "login"));
		repository.save(new User(2l, "User 2", "Alceu", "123456", "login"));
		repository.save(new User(3l, "User 3", "Amadeu", "123456", "login"));
		//repository.flush();
	} 
	
	@After
	public void after(){
		//repository.deleteAll();
		//repository.flush();
	}

	@Test
	public void isControllerAlive() throws Exception {
		assertThat(mvc.perform(MockMvcRequestBuilders.get("/api/user/base"))
				.andReturn()	.getResponse().getStatus())
				.isEqualTo(200);
	}
 
	@Test
	public void getAll() throws Exception {
		long countUsers = repository.count();
		
		MvcResult getResult = mvc
				.perform(
						MockMvcRequestBuilders.get("/api/user/all")
						)
				.andExpect(status().is(200))
				.andReturn();
		
		String content = getResult.getResponse().getContentAsString();
		UserList userList = new ObjectMapper().readValue(content, UserList.class);
		
		assertThat((long) userList.getUsers().size()).isEqualTo(countUsers);
	}

	@Test
	public void getUserByName() throws Exception {
		User user = repository.findByUserId(2l);
		
		MvcResult getResult = mvc
				.perform(
						MockMvcRequestBuilders.get("/api/user/name/" + user.getName())
						)
				.andExpect(status().is(200))
				.andReturn();
		
		String content = getResult.getResponse().getContentAsString();
		UserList userList = new ObjectMapper().readValue(content, UserList.class);
		
		assertThat(user.getName()).isEqualTo(userList.getUsers().get(0).getName());
	}
	
	@Test
	public void getUserById() throws Exception {
		MvcResult getResult = mvc
				.perform(
						MockMvcRequestBuilders.get("/api/user/id/2")
						)
				.andExpect(status().is(200))
				.andReturn();
		
		String content = getResult.getResponse().getContentAsString();
		UserList userList = new ObjectMapper().readValue(content, UserList.class);
		
		assertNotNull(userList.getUsers().get(0));
	}

	@Test
	public void postUser() throws Exception {
		UserCommand newUser = new UserCommand("New Name", "lastName", "pwd", "lgUser");
		long countUserBeforePost = repository.count();

		mvc.perform(
						MockMvcRequestBuilders
						.post("/api/user/add")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(newUser))
						)
				.andExpect(status().is(200)) //Created
				.andReturn();

		assertThat(repository.count()).isNotEqualTo(countUserBeforePost);
	}
	
	@Test
	public void updateUser() throws Exception {
		UserCommand newUser = new UserCommand("Update Name", "lastName", "pwd", "lgUser");
		long countUserBeforePost = repository.count();

		mvc.perform(
				MockMvcRequestBuilders
				.put("/api/user/1/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(newUser))
				)
		.andExpect(status().is(200))
		.andReturn();

		assertThat(repository.count()).isEqualTo(countUserBeforePost);
	}
	
	@Test
	public void deleteUser() throws Exception {
		mvc.perform(
					MockMvcRequestBuilders.delete("/api/user/delete/1")
					)
			.andExpect(status().is(200))
			.andReturn();

		assertNull(repository.findByUserId(1l));
	}
}