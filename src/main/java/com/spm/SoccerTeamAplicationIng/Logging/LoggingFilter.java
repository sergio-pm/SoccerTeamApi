package com.spm.SoccerTeamAplicationIng.Logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);

        String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());

        String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());

        contentCachingRequestWrapper.getInputStream();
        var auth= SecurityContextHolder.getContext().getAuthentication();

        LOGGER.info("Filter Logs : METHOD = {}; REQUESTURI= {}; REQUEST BODY ={}; RESPONSE CODE= {}; MENSAJE= {};" +
                        "DATOS USUARIO= {}; DATOS PERMISOS= {}; AUTENTICADO= {};"
        ,request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody,
                auth.getPrincipal() ,auth.getAuthorities(),auth.isAuthenticated());

        contentCachingResponseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
