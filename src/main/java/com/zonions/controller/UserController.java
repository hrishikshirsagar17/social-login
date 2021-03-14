package com.zonions.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zonions.config.CurrentUser;
import com.zonions.dto.LocalUser;
import com.zonions.model.User;
import com.zonions.service.UserServiceImpl;
import com.zonions.util.GeneralUtils;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  UserServiceImpl service;

  @GetMapping("/user/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
    return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
  }

  @GetMapping("/users")
  public List<User> getAll() {
    return service.getAllUsers();
  }

  @PutMapping("/users/{id}")
  public User changeRole(@PathVariable long id, @RequestBody User regi) {
    return service.changeRole(id, regi);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<HttpStatus> deleteByUsername(@PathVariable long id) {

    return service.deleteById(id);

  }
}
