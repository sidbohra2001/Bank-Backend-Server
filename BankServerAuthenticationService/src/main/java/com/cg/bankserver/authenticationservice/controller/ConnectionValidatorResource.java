package com.cg.bankserver.authenticationservice.controller;

import com.cg.bankserver.authenticationservice.entities.ConnValidationResponse;

import com.cg.bankserver.authenticationservice.entities.User;
import com.cg.bankserver.authenticationservice.exceptions.UsernameAlreadyExistsException;
import com.cg.bankserver.authenticationservice.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.cg.bankserver.authenticationservice.dto.UserDTO;

import java.util.List;

@RestController
@Slf4j
public class ConnectionValidatorResource {

    @Autowired private UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> helloRoute() {
        return ResponseEntity.ok("World");
    }

    @PostMapping(value = {"/check"})
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("Checked");
    }

    @PostMapping(value = {"/add"})
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDto) throws UsernameAlreadyExistsException {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return ResponseEntity.ok(userService.encryptAndSave(user));
    }

    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ConnValidationResponse> validatePost() {
        return ResponseEntity.ok(ConnValidationResponse.builder().status("OK").methodType(HttpMethod.POST.name())
                .isAuthenticated(true).build());
    }

    @GetMapping(value = "/validate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ConnValidationResponse> validateGet(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        int userId = (Integer) request.getAttribute("userId");
        log.info("User id : " + userId);
        String token = (String) request.getAttribute("jwt");
        log.info("username "  + username);
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) request.getAttribute("authorities");
        return ResponseEntity.ok(ConnValidationResponse.builder().status("OK").methodType(HttpMethod.GET.name())
                        .username(username).token(token).authorities(grantedAuthorities)
                        .userId(userId)
                .isAuthenticated(true).build());
    }



    @PostMapping(value = "/whitelisted", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ConnValidationResponse> validateWhitelistedPost() {
        return ResponseEntity.ok(ConnValidationResponse.builder().status("OK").methodType(HttpMethod.POST.name()).build());
    }

    @GetMapping(value = "/whitelisted", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ConnValidationResponse> validateWhitelistedGet(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return ResponseEntity.ok(ConnValidationResponse.builder().status("OK").methodType(HttpMethod.GET.name()).username(username).build());
    }

}