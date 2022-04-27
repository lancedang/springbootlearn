package com.lance.oauth2demo.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

//用于存储资源所属user信息
@Service
public class MyUserDetailService implements UserDetailsService {

    static Collection<GrantedAuthority> grantedAuthorities1 = new ArrayList<>();
    static Collection<GrantedAuthority> grantedAuthorities2 = new ArrayList<>();

    static {

        grantedAuthorities1.add(new SimpleGrantedAuthority("ROLE_USER"));

        grantedAuthorities2.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        grantedAuthorities2.add(new SimpleGrantedAuthority("ROLE_USER"));
    }


    private final static Set<User> users = new HashSet<>();

    static {
        //user1只有一个ROLE_USER权限
        //user2有2个权限：ROLE_ADMIN，ROLE_USER
        //这个权限会和：@PreAuthorize("hasRole('ROLE_ADMIN')") 中hasRole绑定
        users.add(new User("user1", "pwd1", grantedAuthorities1));
        users.add(new User("user2", "pwd2", grantedAuthorities2));
    }

    //这个方法会在client请求获取access-token时执行
    //client请求获取access-token时会携带username和pwd
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> first = users.stream().filter(item -> item.getUsername().equals(username))
                .findFirst();

        if (first.isPresent()) {
            User user = first.get();
            return new MyUserDetails(user.getUsername(), user.getPassword(), user.getAuthorities());
        }else {
            throw new UsernameNotFoundException("no name");
        }

    }
}
