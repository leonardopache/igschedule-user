/**
 * 
 */
package com.pache.igscheduleuser.controller.resourcesuporte;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.pache.igscheduleuser.controller.UserController;

/**
 * @author lpache
 *
 */
public class UserList {
	
	private List<User> userList = new ArrayList<>();

	public UserList() {
		// TODO Auto-generated constructor stub
	}
	
	public UserList(List<com.pache.igscheduleuser.entity.User> users) {
		List<User> embededUserList = new ArrayList<>();
		for (com.pache.igscheduleuser.entity.User userItem : users) {
			User user = new User();
			user.setName(userItem.getName());
			user.setLastName(userItem.getLastName());
			user.add(ControllerLinkBuilder.linkTo(UserController.class).slash(userItem.getUserId().toString()).withSelfRel());
			embededUserList.add(user);
		}
		setUserList(embededUserList);
	}
	
	public UserList(com.pache.igscheduleuser.entity.User user) {
		List<User> embededUserList = new ArrayList<>();
		User embededUser = new User();
		embededUser.setName(user.getName());
		embededUser.setLastName(user.getLastName());
		embededUser.add(ControllerLinkBuilder.linkTo(UserController.class).slash(user.getUserId().toString()).withSelfRel());
		embededUserList.add(embededUser);
		setUserList(embededUserList);
	}

	public List<User> getUserList() {
		return userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public static class User extends ResourceSupport {
		private String name;
		private String lastName;
		
		public User() {
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}
}
