package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserRepository;
import com.mysql.cj.Session;

import groovyjarjarpicocli.CommandLine.Model;

@Service
public class CustomerUserDetailService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;
	
//	@Autowired
//	Session session;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        
        
        //session.
//
//		System.out.println(user.getUsername());
//		System.out.println(user.getPassword());
//		System.out.println(user.getRole());
        return new MyUserPrincipal(user);
    }

}
