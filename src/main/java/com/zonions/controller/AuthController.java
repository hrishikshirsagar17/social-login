package com.zonions.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zonions.dto.ApiResponse;
import com.zonions.dto.JwtAuthenticationResponse;
import com.zonions.dto.LocalUser;
import com.zonions.dto.LoginRequest;
import com.zonions.dto.SignUpRequest;
import com.zonions.exception.UserAlreadyExistAuthenticationException;
import com.zonions.security.jwt.TokenProvider;
import com.zonions.service.UserService;
import com.zonions.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserService userService;

  @Autowired
  TokenProvider tokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.createToken(authentication);
    LocalUser localUser = (LocalUser) authentication.getPrincipal();
    @SuppressWarnings("unchecked")
    List<LocalUser> sessionUser =
        (List<LocalUser>) request.getSession().getAttribute("SESSION_USER");
    if (sessionUser == null) {
      sessionUser = new ArrayList<>();

      request.getSession().setAttribute("SESSION_USER", localUser);
      request.getSession().setMaxInactiveInterval(10 * 60);

    }

    sessionUser.add(localUser);
    request.getSession().setAttribute("SESSION_USER", sessionUser);


    return ResponseEntity
        .ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    try {
      userService.registerNewUser(signUpRequest);
    } catch (UserAlreadyExistAuthenticationException e) {
      log.error("Exception Ocurred", e);
      return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
          HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
  }
}
