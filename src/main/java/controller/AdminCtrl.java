package controller;
import dao.AdminDAO;
import model.Admin;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminCtrl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO adminDAO;
    
    @Override
    public void init() throws ServletException {
        this.adminDAO = new AdminDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println("Admin login attempt - Username: " + username);
        
        Admin admin = adminDAO.authenticateAdmin(username, password);
        
        if (admin != null) {
            // Login successful - create session
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            session.setAttribute("adminName", admin.getFullName());
            session.setAttribute("adminUsername", admin.getUsername());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
            
            System.out.println("Admin login successful: " + admin.getFullName());
            
            // Redirect to admin dashboard
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
        } else {
            // Login failed
            System.out.println("Admin login failed for username: " + username);
            request.setAttribute("errorMessage", "Invalid admin ID or password!");
            request.getRequestDispatcher("/auth/login/AdminLogin.jsp").forward(request, response);
        }
    }
}