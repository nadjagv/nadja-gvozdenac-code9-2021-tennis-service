package com.nadjagv.playerservice.config;

import com.nadjagv.adminloginservice.repository.AdminRepository;
import com.nadjagv.adminloginservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.nadjagv.adminloginservice.security.auth.RestAuthenticationEntryPoint;
import com.nadjagv.adminloginservice.security.auth.TokenAuthenticationFilter;

import com.nadjagv.adminloginservice.util.TokenUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(HttpSecurity security) throws Exception
//	{
//		security.httpBasic().disable();
//	}

	@Bean RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
		return new RestAuthenticationEntryPoint();
	}

	@Bean TokenUtils tokenUtils(){
		return new TokenUtils();
	}

	@Bean AdminService adminService(){
		return new AdminService();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint()).and()

				.authorizeRequests().antMatchers("/api/players/**").permitAll()

									.antMatchers("/**").permitAll()

				.anyRequest().authenticated().and()

				.cors().and()

				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils(), adminService()), BasicAuthenticationFilter.class);

		http.csrf().disable();
	}
}
