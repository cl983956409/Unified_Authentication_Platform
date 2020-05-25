package com.server.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务器
 *
 * @author 程龙[chenglonghy]
 * @date 2020/5/15 - 16:47
 * @history 2020/5/15 - 16:47 chenglonghy  create.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;
    @Autowired
    ClientDetailsService clientDetailsService;

    /**
     * tokenServices 用来配置 Token 的一些基本信息
     * 例如 Token 是否支持刷新、Token 的存储位置、Token 的有效期以及刷新 Token 的有效期等等
     * Token 有效期这个好理解，刷新 Token 的有效期我说一下：当 Token 快要过期的时候，我们需要获取一个新的 Token，
     * 在获取新的 Token 时候，需要有一个凭证信息，这个凭证信息不是旧的 Token，而是另外一个refresh_token，这个 refresh_token 也是有有效期的。
     *
     * @return
     */
    @Bean
    AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(60 * 60 * 2);
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return services;
    }

    /**
     * AuthorizationServerSecurityConfigurer 用来配置用户的详细信息
     * 配置令牌端点的安全约束(校验用户)，也就是这个端点谁能访问，谁不能访问。
     * checkTokenAccess 是指一个 Token 校验的端点，这个端点我们设置为可以直接访问
     * （在后面，当资源服务器收到 Token 之后，需要去校验 Token 的合法性，就会访问这个端点）。
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * ClientDetailsServiceConfigurer 用来配置客户端的详细信息
     * 授权服务器要做两方面的检验，一方面是校验客户端，另一方面则是校验用户;这里就是配置校验客户端
     * 客户端的信息我们可以存在数据库中，这其实也是比较容易的，和用户信息存到数据库中类似
     * 暂时将客户端信息存在内存中，这里我们分别配置了客户端的 id，secret、资源 id、授权类型、授权范围以及重定向 uri。
     * 授权类型有四种，四种之中不包含 refresh_token 这种类型，但是在实际操作中，refresh_token 也被算作一种。
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("javaboy")
                .secret(new BCryptPasswordEncoder().encode("123"))
                .resourceIds("res1")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .redirectUris("http://172.23.15.208:8092/index.html");
    }

    /**
     * AuthorizationServerEndpointsConfigurer 用来配置令牌的访问端点和令牌服务
     * authorizationCodeServices 用来配置授权码的存储
     * tokenServices 用来配置令牌的存储，即 access_token 的存储位置
     * <p>
     * todo 授权码和令牌有什么区别？授权码是用来获取令牌的，使用一次就失效，令牌则是用来获取资源的。
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices());
    }

    /**
     * authorizationCodeServices 用来配置授权码的存储，这里我们是存在在内存中，
     *
     * @return
     */
    @Bean
    AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
}
