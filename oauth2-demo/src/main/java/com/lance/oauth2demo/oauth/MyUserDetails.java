package com.lance.oauth2demo.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails extends User{

    public MyUserDetails(String userName,String pwd,  Collection<GrantedAuthority> grantedAuthorities) {
        super(userName, pwd, grantedAuthorities);
    }

}
