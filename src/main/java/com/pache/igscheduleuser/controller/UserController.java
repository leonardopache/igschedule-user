package com.pache.igscheduleuser.controller;

import java.net.URI;
import java.net.URISyntaxException;

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
import com.pache.igscheduleuser.repository.UserRepository;

/**
 * Created by lpache on 7/18/17.
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/users/base")
    public String getNameController() {
        return this.getClass().getSimpleName().toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/all")
    @ResponseBody
    public UserList getAllUsers() {
        return new UserList(repository.findAll());
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    @ResponseBody
    public UserList getUser(@PathVariable Long userId) {
        return new UserList(repository.getOne(userId));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/users/{userName}")
    @ResponseBody
    public UserList getUser(@PathVariable String userName) {
        return  new UserList(repository.findByName(userName));
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/user/add")
    public ResponseEntity<?> addUser(@RequestBody UserCommand userCommand) throws URISyntaxException {
        User user = UserCommand.transformerUserCommandToUser(userCommand);
        user = repository.save(user);
        Link link = ControllerLinkBuilder.linkTo(UserController.class).slash(user.getUserId().toString()).withSelfRel();
        HttpHeaders headers = new HttpHeaders();
		URI location = new URI(link.getHref());
		headers.setLocation(location);
		return ResponseEntity.created(location).body(user);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userId}/add")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserCommand userCommand) throws URISyntaxException {
        User user = UserCommand.transformerUserCommandToUser(userCommand);
        //user.setUserId(userId);
        user = repository.save(user);
        Link link = ControllerLinkBuilder.linkTo(UserController.class).slash(user.getUserId().toString()).withSelfRel();
        HttpHeaders headers = new HttpHeaders();
		URI location = new URI(link.getHref());
		headers.setLocation(location);
		return ResponseEntity.created(location).body("Success!!");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        repository.deleteById(userId);
    }

}
