package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // Get existing session, don't create a new one
        HttpSession session = req.getSession(false);
        
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        
        System.out.println("=== ADMIN FILTER DEBUG ===");
        System.out.println("Request URI: " + requestURI);
        System.out.println("Context Path: " + contextPath);
        System.out.println("Session: " + session);
        
        if (session != null) {
            System.out.println("Session ID: " + session.getId());
            System.out.println("Admin attribute: " + session.getAttribute("admin"));
            System.out.println("All session attributes:");
            java.util.Enumeration<String> attrNames = session.getAttributeNames();
            while (attrNames.hasMoreElements()) {
                String name = attrNames.nextElement();
                System.out.println("  " + name + " = " + session.getAttribute(name));
            }
        }
        
        // Check if this is a login-related request that should be excluded
        boolean isLoginPage = requestURI.equals(contextPath + "/auth/login/AdminLogin.jsp");
        boolean isLoginServlet = requestURI.equals(contextPath + "/AdminLoginServlet");
        boolean isStaticResource = requestURI.contains("/css/") || requestURI.contains("/js/") || requestURI.contains("/images/");
        
        System.out.println("isLoginPage: " + isLoginPage);
        System.out.println("isLoginServlet: " + isLoginServlet);
        System.out.println("isStaticResource: " + isStaticResource);
        
        if (isLoginPage || isLoginServlet || isStaticResource) {
            System.out.println("ALLOWING: Login page or static resource");
            chain.doFilter(request, response);
            return;
        }
        
        // Check if admin is authenticated
        boolean isAdminLoggedIn = (session != null && session.getAttribute("admin") != null);
        System.out.println("isAdminLoggedIn: " + isAdminLoggedIn);
        
        if (isAdminLoggedIn) {
            System.out.println("ALLOWING: Admin is authenticated");
            chain.doFilter(request, response);
        } else {
            System.out.println("REDIRECTING: No admin session");
            res.sendRedirect(contextPath + "/auth/login/AdminLogin.jsp");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AdminAuthenticationFilter initialized");
    }

    @Override
    public void destroy() {
        System.out.println("AdminAuthenticationFilter destroyed");
    }
}