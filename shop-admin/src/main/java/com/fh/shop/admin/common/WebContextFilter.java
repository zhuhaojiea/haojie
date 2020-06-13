package com.fh.shop.admin.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class WebContextFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            //存值
            WebContext.set((HttpServletRequest) req);
            chain.doFilter(req, resp);
        } finally {
            //删除
            WebContext.del();
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
