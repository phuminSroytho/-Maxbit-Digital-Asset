package com.example.maxbitdigitalasset.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@Component
public class HttpHeaderHelper {
    @Bean
    @RequestScope
    public HttpHeaders httpHeaders() {
        HttpHeaders httpHeader = new HttpHeaders();
        HttpServletRequest curRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration<String> headerName = curRequest.getHeaderNames();
        if (headerName != null) {
            while (headerName.hasMoreElements()) {
                String header = headerName.nextElement();
                String value = curRequest.getHeader(header);
                httpHeader.add(header, value);
            }
        }

        return httpHeader;
    }
}
