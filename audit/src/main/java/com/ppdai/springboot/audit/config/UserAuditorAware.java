package com.ppdai.springboot.audit.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component //必须将 AuditorAware 实例作为 bean 注入到上下文
public class UserAuditorAware implements AuditorAware<String> {

    public static final String DEFAULT_SYSTEM_NAME = "system";


    @Override
    public String getCurrentAuditor() {
        String userName = DEFAULT_SYSTEM_NAME;

        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();


        } catch (Exception e) {

        }

        return userName;
    }

}
