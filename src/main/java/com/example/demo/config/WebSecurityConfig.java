package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.demo.service.CustomLogoutHandler;
import com.example.demo.service.CustomerUserDetailService;
import com.example.demo.service.LoginSuccessHandler;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private CustomLogoutHandler logoutHandler;
	@Autowired
	CustomerUserDetailService customerUserDetailService;

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/css/**", "/images/**", "/img/**").permitAll();
		http.authorizeRequests().antMatchers("/homeuser").hasAuthority("USER");
		http.authorizeRequests().antMatchers("/signup", "/create-account").permitAll();
		http.authorizeRequests().antMatchers("/users/homepage", "/users/shopping/**", "/users/shopping/search")
				.permitAll();
		http.authorizeRequests().antMatchers("/clothes/**", "/home-admin").hasAuthority("ADMIN");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		http
				.authorizeRequests()
				.antMatchers("/").permitAll() // Cho phép tất cả mọi người truy cập vào 2 địa chỉ này
				.anyRequest().authenticated() // Tất cả các request khác đều cần phải xác thực mới được truy cập
				.and()
				.formLogin() // Cho phép người dùng xác thực bằng form login
				//.loginProcessingUrl("/j_spring_security_check") // Submit URL
				//.loginPage("/login")
				.successHandler(loginSuccessHandler)
				// .defaultSuccessUrl("/home")
				.failureForwardUrl("/")
				//.permitAll() // Tất cả đều được truy cập vào địa chỉ này
				.and()
				.logout() // Cho phép logout
				.addLogoutHandler(logoutHandler)
				.logoutSuccessUrl("/users/homepage-login")
				.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customerUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		// Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
		return new BCryptPasswordEncoder();
	}

}