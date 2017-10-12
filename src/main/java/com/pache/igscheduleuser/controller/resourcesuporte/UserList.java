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
	
	private List<User> users = new ArrayList<>();

	public UserList() {
	}
	
	public UserList(List<com.pache.igscheduleuser.entity.User> users) {
		List<User> embededUserList = new ArrayList<>();
		for (com.pache.igscheduleuser.entity.User userItem : users) {
			embededUserList.add(transformUserToUserList(userItem));
		}
		setUsers(embededUserList);
	}

	public UserList(com.pache.igscheduleuser.entity.User user) {
		List<User> embededUserList = new ArrayList<>();
		embededUserList.add(transformUserToUserList(user));
		setUsers(embededUserList);
	}
	
	private User transformUserToUserList(com.pache.igscheduleuser.entity.User userItem) {
		User user = new User();
		user.setName(userItem.getName());
		user.setLastName(userItem.getLastName());
		user.add(ControllerLinkBuilder.linkTo(UserController.class).slash("id").slash(userItem.getUserId().toString()).withSelfRel());
		return user;
	}

	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> userList) {
		this.users = userList;
	}

	public static class User extends ResourceSupport {
		
		private String name;
		private String lastName;
		
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
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			User other = (User) obj;
			if (lastName == null) {
				if (other.lastName != null)
					return false;
			} else if (!lastName.equals(other.lastName))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}
}
