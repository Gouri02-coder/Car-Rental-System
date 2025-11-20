package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter("/*")
public class SessionTimeoutFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        if (session == null) {
            chain.doFilter(request, response);
            return;
        }
        
        // Check if session has expired
        long currentTime = System.currentTimeMillis();
        long lastAccessTime = session.getLastAccessedTime();
        int maxInactiveInterval = session.getMaxInactiveInterval();
        
        if (currentTime - lastAccessTime > maxInactiveInterval * 1000) {
            session.invalidate();
            if (httpRequest.getRequestURI().contains("/admin/")) {
                // FIXED PATH: Redirect to correct admin login page
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login/AdminLogin.jsp?timeout=true");
                return;
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login/UserLogin.jsp?timeout=true");
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Cleanup code
    }
}