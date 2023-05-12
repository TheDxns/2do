package io.github.thedxns.todo.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@KeycloakConfiguration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        User user = new User("Jan",
                getBcryptPasswordEncoder().encode("jan123"),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        User admin = new User("admin",
                getBcryptPasswordEncoder().encode("admin123"),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));

        auth.inMemoryAuthentication().withUser(user);
        auth.inMemoryAuthentication().withUser(admin);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/tasks").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/lists").hasAuthority("ROLE_ADMIN")
                .and()
                .formLogin().permitAll()
                .and()
                .logout().logoutUrl("/bye");

    }
}