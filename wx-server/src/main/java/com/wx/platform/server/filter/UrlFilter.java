package com.wx.platform.server.filter;

import com.wx.platform.core.util.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@WebFilter(urlPatterns = {"/*"})
@Order(-1)
public class UrlFilter extends OncePerRequestFilter {

    private Logger LOG = LoggerFactory.getLogger(UrlFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestUrl = httpServletRequest.getRequestURI();
        if (!requestUrl.contains(".")) {
            String url = httpServletRequest.getRequestURL().toString();
            String ip = HttpUtils.getIpAddress(httpServletRequest);
            String method = httpServletRequest.getMethod();
            InputStream in = httpServletRequest.getInputStream();
            String result = IOUtils.toString(in, "UTF-8");
            String message = "the request url:[" + ip + "][" + method + "] " + url;
            if (!StringUtils.isNotEmpty(result)) {
                message += "[" + result + "]";
            }
            LOG.info(message);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
