package com.pache.igscheduleuser.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pache.igscheduleuser.controller.resourcesuporte.UserList;
import com.pache.igscheduleuser.entity.User;
import com.pache.igscheduleuser.exception.ApplicationException;
import com.pache.igscheduleuser.repository.UserRepository;

/**
 * Created by lpache on 7/18/17.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/base")
    public String getNameController() {
        return this.getClass().getSimpleName().toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public UserList getAllUsers() {
        return new UserList(repository.findAll());
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    @ResponseBody
    public User getUser(@PathVariable Long userId) {
        return (User) repository.getOne(userId);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{userName}")
    @ResponseBody
    public UserList getUser(@PathVariable String userName) {
        return  new UserList(repository.findByName(userName));
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<?> addUser(@RequestBody UserCommand userCommand, HttpServletRequest request) {
        User user = transformerUserCommandToUser(userCommand);
        user = repository.save(user);
        Link link = ControllerLinkBuilder.linkTo(UserController.class).slash(user.getUserId().toString()).withSelfRel();
        HttpHeaders headers = new HttpHeaders();
        try {
			URI location = new URI(link.getHref());
			headers.setLocation(location);
			return ResponseEntity.created(location).body(user);
		} catch (URISyntaxException e) {
			throw new ApplicationException(e.getMessage());
		}
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{userId}/add")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserCommand userCommand, HttpServletRequest request) {
        User user = transformerUserCommandToUser(userCommand);
        user.setUserId(userId);
        user = repository.save(user);
        Link link = ControllerLinkBuilder.linkTo(UserController.class).slash(user.getUserId().toString()).withSelfRel();
        HttpHeaders headers = new HttpHeaders();
        try {
			URI location = new URI(link.getHref());
			headers.setLocation(location);
			return ResponseEntity.created(location).body("Success!!");
		} catch (URISyntaxException e) {
			throw new ApplicationException(e.getMessage());
		}
    }

    private User transformerUserCommandToUser(UserCommand userCommand) {
        User user = new User();
        user.setName(userCommand.getName());
        user.setLastName(userCommand.getLastName());
        user.setPassword(userCommand.getPassword());
        user.setLogin(userCommand.getLogin());
        return user;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        repository.deleteById(userId);
    }

}
