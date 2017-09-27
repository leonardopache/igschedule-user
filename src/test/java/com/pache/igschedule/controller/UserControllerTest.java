package com.pache.igschedule.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pache.igschedule.config.TestConfiguration;
import com.pache.igscheduleuser.controller.UserController;
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

	// inject on controller our Mocked repository
	@Autowired
	@InjectMocks
	private UserController userController;

	@Mock
	private UserRepository repository;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		MockitoAnnotations.initMocks(this);
	}

	private User newInstaceUser(long userId, String name, String lastName) {
		User user = new User();
		user.setUserId(userId);
		user.setName(name);
		user.setLastName(lastName);
		return user;
	}

	@Test
	public void getNameController() throws Exception {
		assertThat(mvc.perform(MockMvcRequestBuilders.get("/users/base"))
				.andReturn()	.getResponse().getStatus())
				.isEqualTo(200);
	}

	@Test
	public void getAll() throws Exception {
		List<User> listUser = new ArrayList<>();
		listUser.add(newInstaceUser(1l,"Leonardo", "Pache"));
		when(repository.findAll()).thenReturn(listUser);

		MvcResult getResult = mvc.perform(MockMvcRequestBuilders.get("/users/all"))
				.andExpect(status().is(200)).andReturn();
		
		String content = getResult.getResponse().getContentAsString();
		new ObjectMapper().readValue(content, UserList.class);
		assertThat(new ObjectMapper().readValue(content, UserList.class).getUserList().size()).isEqualTo(1);
	}

	@Test
	public void getUserByName() throws Exception {
		List<User> listUser = new ArrayList<>();
		User newInstaceUser = newInstaceUser(1l,"Leonardo", "Pache");
		listUser.add(newInstaceUser);
		UserRepository mock = org.mockito.Mockito.mock(UserRepository.class);
		when(mock.findByName(newInstaceUser.getName())).thenReturn(listUser);
		// Mockito.when(repository.findByName(newInstaceUser.getName())).thenReturn(listUser);

		mvc.perform(MockMvcRequestBuilders.get("/users/" + newInstaceUser.getName()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andReturn();
	}

	@Test
	public void postUser() throws Exception {
		
		Mockito.when(repository.save((User)any(User.class))).thenReturn(newInstaceUser(1L, "Leonardo", "Pache"));
		MvcResult getResult = mvc
				.perform(MockMvcRequestBuilders.post("/users/add").accept(MediaType.APPLICATION_JSON).content(""))
				.andExpect(status().is(200)).andReturn();

		String content = getResult.getResponse().getContentAsString();
	}

}