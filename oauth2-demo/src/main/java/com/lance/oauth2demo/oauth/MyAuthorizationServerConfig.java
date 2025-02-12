package com.lance.oauth2demo.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    public AuthenticationManager authenticationManager;


    //存储access token
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

/*    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }*/

    //配置client，请求方相关信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //super.configure(clients);
        clients.inMemory()
                .withClient("client_id")
                .resourceIds("")
                .scopes("read")
                //.authorities("ROLE_USER_1", "ROLE_ADMIN_1")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .secret("scerect1")
                .accessTokenValiditySeconds(60 * 600)
                .refreshTokenValiditySeconds(60 * 600)
                .redirectUris("http://baidu.com");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
        //.userDetailsService(myUserDetailService);
        //super.configure(endpoints);
    }
}
