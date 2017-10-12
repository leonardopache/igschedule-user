package com.pache.igscheduleuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pache.igscheduleuser.controller.resourcesuporte.UserList;
import com.pache.igscheduleuser.entity.User;
import com.pache.igscheduleuser.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Created by lpache on 7/18/17.
 */
@RestController
@RequestMapping("/api/user")
@Api(value="API to User operations")
public class UserController {

    @Autowired
    private UserRepository repository;

    @ApiOperation(value = "View name of controlle", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")}
    )
    @RequestMapping(method = RequestMethod.GET, value = "/base")
    public String getNameController() {
        return this.getClass().getSimpleName();
    }

    @ApiOperation(value = "View a list of available users", response = UserList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")}
    )
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public UserList getAllUsers() {
        return new UserList(repository.findAll());
    }
    
    @ApiOperation(value = "Find user by ID", response = UserList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")}
    )
    @RequestMapping(method = RequestMethod.GET, value = "/id/{userId}")
    @ResponseBody
    public UserList getUser(@PathVariable Long userId) {
        return new UserList(repository.getOne(userId));
    }
    
    @ApiOperation(value = "Find user by Name", response = UserList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")}
    )
    @RequestMapping(method = RequestMethod.GET, value = "/name/{userName}")
    @ResponseBody
    public UserList getUser(@PathVariable String userName) {
        return  new UserList(repository.findByName(userName));
    }
    
    @ApiOperation(value = "Create a new user", response = UserList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")}
    )
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @ResponseBody
    public UserList addUser(@RequestBody UserCommand userCommand) {
        User user = UserCommand.transformerUserCommandToUser(userCommand);
        user = repository.save(user);
        return new UserList(user);
    }
    
    @ApiOperation(value = "Update the user", response = UserList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")}
    )
    @RequestMapping(method = RequestMethod.PUT, value = "/{userId}/add")
    @ResponseBody
    public UserList updateUser(@PathVariable Long userId, @RequestBody UserCommand userCommand) {
        User user = UserCommand.transformerUserCommandToUser(userCommand);
        user.setUserId(userId);
        user = repository.save(user);
        return new UserList(user);
    }

    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")}
    )
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        repository.deleteById(userId);
    }

}
