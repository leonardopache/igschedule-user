package com.pache.igschedule.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pache.igschedule.config.TestConfiguration;
import com.pache.igscheduleuser.entity.User;
import com.pache.igscheduleuser.repository.UserRepository;

/**
 * Created by lpache on 7/18/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class UserControllerTest {

	@Autowired
	private WebApplicationContext context;
	
//	@Mock
	@Autowired
	private UserRepository repository;

	private MockMvc mvc;
	private User user;
	
	@Before
	public void setUp() {
		user = newInstaceUser("Leonardo", "Pache");
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
//		MockitoAnnotations.initMocks(this);
	}

	private User newInstaceUser(String name, String lastName) {
		User user = new User();
		user.setName(name);
		user.setLastName(lastName);
		return user;
	}

	@Test
	public void getNameController() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/users/base")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is(200));
	}

	@Test
	public void getAll() throws Exception {
		List<User> listUser = new ArrayList<>();
		User newInstaceUser = newInstaceUser("Leonardo", "Pache");
		listUser.add(newInstaceUser);
		UserRepository mock = org.mockito.Mockito.mock(UserRepository.class);
		when(mock.findByName(newInstaceUser.getName())).thenReturn(listUser);
		
		//Mockito.when(repository.findAll()).thenReturn(listUser);

		MvcResult getResult = mvc.perform(MockMvcRequestBuilders.get("/users/all")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andReturn();
		String content = getResult.getResponse().getContentAsString();
		assertNotNull(content);
	}
	
	@Test
	public void getUserByName() throws Exception {
		List<User> listUser = new ArrayList<>();
		User newInstaceUser = newInstaceUser("Leonardo", "Pache");
		listUser.add(newInstaceUser);
		UserRepository mock = org.mockito.Mockito.mock(UserRepository.class);
		when(mock.findByName(newInstaceUser.getName())).thenReturn(listUser);
		//Mockito.when(repository.findByName(newInstaceUser.getName())).thenReturn(listUser);

		mvc.perform(MockMvcRequestBuilders.get("/users/"+newInstaceUser.getName())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andReturn();
	}

	@Test
	public void postUser() throws Exception {
		User newUser = user;
		newUser.setUserId(1L);
		Mockito.when(repository.save(user)).thenReturn(newUser);
		MvcResult getResult = mvc.perform(MockMvcRequestBuilders.post("/users/add")
				.accept(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().is(200))
				.andReturn();
		
		 String content = getResult.getResponse().getContentAsString();
	}

}