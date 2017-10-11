package com.pache.igscheduleuser.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by lpache on 7/20/17.
 */
@Entity
public class User {

	@Id
    @GeneratedValue
    private Long userId;

    private String name;

    private String lastName;

    private String password;

    private String login;
    
    public User() {
	}
    
  	public User(Long userId, String name, String lastName, String pwd, String login) {
  		this.userId = userId;
  		this.name = name;
  		this.lastName = lastName;
  		this.login = login;
  		this.password =pwd;
  	}

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
}
