package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> usersList() {
        return this.userDao.findAll();
    }

    @RequestMapping(path = "/users/{username}/user_id?=", method = RequestMethod.GET)
    public long findIdByUsername(@Valid String userName){
        return this.userDao.findIdByUsername(userName);
    }

    @RequestMapping(path = "/users/username?=", method = RequestMethod.GET)
    public User findUserByUsername(@Valid String userName){
        return this.userDao.findByUsername(userName);
    }

}
