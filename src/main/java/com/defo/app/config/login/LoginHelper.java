package com.defo.app.config.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginHelper {

    private final String header = "X-Forwarded-For";
    private final String separator = ",";

    @Autowired
    private LoginAttemptService loginAttemptService;

    public boolean isBlocked(HttpServletRequest request) {
        String clientIP = getClientIP(request);
        return loginAttemptService.isBlocked(clientIP);
    }

    public void registerFailedAttempt(HttpServletRequest request) {
        loginAttemptService.loginFailed(getClientIP(request));
    }

    public void registerSuccessAttempt(HttpServletRequest request) {
        loginAttemptService.loginSucceeded(getClientIP(request));
    }

    public String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader(header);
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(separator)[0];
    }

}
