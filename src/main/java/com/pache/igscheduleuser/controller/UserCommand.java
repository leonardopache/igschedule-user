package com.pache.igscheduleuser.controller;

import com.pache.igscheduleuser.entity.User;

/**
 * Created by lpache on 7/21/17.
 */
public class UserCommand {

	private Long id;
    private String name;
    private String lastName;
    private String password;
    private String login;
    
    public UserCommand() {
		// TODO Auto-generated constructor stub
	}
    
    public UserCommand(Long id, String name, String lastName, String password, String login) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.login = login;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public static User transformerUserCommandToUser(UserCommand userCommand) {
        User user = new User();
        user.setUserId(userCommand.getId());
        user.setName(userCommand.getName());
        user.setLastName(userCommand.getLastName());
        user.setPassword(userCommand.getPassword());
        user.setLogin(userCommand.getLogin());
        return user;
    }
   
}
