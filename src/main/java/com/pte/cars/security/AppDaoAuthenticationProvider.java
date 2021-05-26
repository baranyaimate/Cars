package com.pte.cars.security;

import com.pte.cars.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;

    public AppDaoAuthenticationProvider(){
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userService);
    }
}
