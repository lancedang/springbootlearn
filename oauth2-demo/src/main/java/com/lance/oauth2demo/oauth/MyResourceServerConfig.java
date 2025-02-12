package com.lance.oauth2demo.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    //@Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("oauth-demo")
                //.tokenStore(tokenStore)
                .stateless(true);
    }

    //配置哪些request需要权限验证，哪些不需要验证
    //这里的antMatchers 匹配的url层面上的东西，粒度可粗可细（粗就是java controller整个类级别，细可以到java controller某个方法-method）
    //spring-security提供了 @EnableGlobalMethodSecurity用于在通过注解方式在-类级别和method级别进行权限控制（这样的话下面的antMatchers
    //就不需变动了
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //.access("#oauth2.hasScope('read')")
        http.authorizeRequests()
                .antMatchers("/api/test/**")
                .access("#oauth2.hasScope('read')")
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                //.authenticated()
                //.antMatchers("/api/other/**").permitAll();
    }
}
