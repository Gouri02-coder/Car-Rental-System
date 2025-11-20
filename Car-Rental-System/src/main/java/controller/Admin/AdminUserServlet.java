package controller.Admin;

import services.UserService;
import model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private UserService userService;
    
    @Override
    public void init() {
        userService = new UserService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listUsers(request, response);
                break;
            case "view":
                viewUser(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                listUsers(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("update".equals(action)) {
            updateUser(request, response);
        } else if ("toggleStatus".equals(action)) {
            toggleUserStatus(request, response);
        }
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get the logged-in user ID from session
        HttpSession session = request.getSession(false);
        Integer loggedInUserId = null;
        
        if (session != null) {
            // Try to get user ID from session
            Object userIdObj = session.getAttribute("userId");
            if (userIdObj instanceof Integer) {
                loggedInUserId = (Integer) userIdObj;
            } else if (userIdObj instanceof String) {
                try {
                    loggedInUserId = Integer.parseInt((String) userIdObj);
                } catch (NumberFormatException e) {
                    // Ignore if it's not a valid number
                }
            }
            
            // If not found, try adminId
            if (loggedInUserId == null) {
                Object adminIdObj = session.getAttribute("adminId");
                if (adminIdObj instanceof Integer) {
                    loggedInUserId = (Integer) adminIdObj;
                } else if (adminIdObj instanceof String) {
                    try {
                        loggedInUserId = Integer.parseInt((String) adminIdObj);
                    } catch (NumberFormatException e) {
                        // Ignore if it's not a valid number
                    }
                }
            }
        }
        
        // Get all users from database
        List<User> users = userService.getAllUsers();
        
        // Set attributes for JSP
        request.setAttribute("users", users);
        request.setAttribute("loggedInUserId", loggedInUserId);
        
        // CORRECT PATH: /admin/users/manage-users.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/users/manage-users.jsp");
        dispatcher.forward(request, response);
    }
    
    private void viewUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserById(userId);
        request.setAttribute("user", user);
        // CORRECT PATH: /admin/users/user-details.jsp
        request.getRequestDispatcher("/admin/users/user-details.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserById(userId);
        request.setAttribute("user", user);
        // CORRECT PATH: /admin/users/edit-user.jsp
        request.getRequestDispatcher("/admin/users/edit-user.jsp").forward(request, response);
    }
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String licenseNumber = request.getParameter("licenseNumber");

        boolean success = userService.updateUser(userId, email, phone, licenseNumber);

        if (success) {
            response.sendRedirect("users?action=list&message=User updated successfully");
        } else {
            response.sendRedirect("users?action=list&error=Failed to update user");
        }
    }
    
    private void toggleUserStatus(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        boolean success = userService.toggleUserStatus(userId);

        if (success) {
            response.sendRedirect("users?action=list&message=User status updated successfully");
        } else {
            response.sendRedirect("users?action=list&error=Failed to update user status");
        }
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        boolean success = userService.deleteUser(userId);

        if (success) {
            response.sendRedirect("users?action=list&message=User deleted successfully");
        } else {
            response.sendRedirect("users?action=list&error=Failed to delete user");
        }
    }
}