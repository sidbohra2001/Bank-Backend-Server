package com.cg.bankserver.authenticationservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import com.cg.bankserver.authenticationservice.dto.ApplicationUser;
import com.cg.bankserver.authenticationservice.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService usersService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = new ApplicationUser(usersService.getByUsrName(s).orElseThrow(() -> new UsernameNotFoundException("Username Not Found")));
        System.out.println(user.getUsername() + "  " + user.getAuthorities());
        return user;
    }
}