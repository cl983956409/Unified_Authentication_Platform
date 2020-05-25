package com.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/5/15 - 16:46
 * @history 2020/5/15 - 16:46 chenglonghy  create.
 */
@Configuration
public class AccessToken {

    /**
     * TokenStore 的实例，这个是指你生成的 Token 要往哪里存储，我们可以存
     * 在 Redis 中，也可以存在内存中，也可以结合 JWT 等等，这里我们先把它存在内存中
     *
     * @return
     */
    @Bean
    TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

}
