package com.defo.app.config.token;

import com.defo.app.model.User;
import com.defo.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

@Component
public class TokenProvider {

    @Autowired
    private RedisTemplate<String, String> redis;

    private String generateNewToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private void storeTokenInRedis(String tokenValue, String username) {
        redis.opsForValue().append(tokenValue, username);
        redis.expireAt(tokenValue, LocalDateTime.now().plusSeconds(Constants.EXPIRATION_TIME).atZone(ZoneId.systemDefault()).toInstant());
    }

    public String generateTokenForUser(User user) {
        String token = generateNewToken();
        storeTokenInRedis(token, user.getUsername());
        return token;
    }

    public String getLoginByToken(String tokenValue) {
        return redis.opsForValue().get(tokenValue);
    }

    public boolean isAuthenticated(String tokenValue) {
        return redis.opsForValue().get(tokenValue) != null;
    }

    public void releaseToken(HttpServletRequest request) {
        String tokenValue = getTokenFromRequest(request);
        redis.delete(tokenValue);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(Constants.AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith(Constants.BEARER)) {
            return bearer.substring(Constants.BEARER.length());
        }
        return null;
    }

}
