package controller.Admin;  // FIXED: Use proper package name

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
            toggleUserStatus(request, response);  // FIXED: Correct method name
        }
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
    	    throws ServletException, IOException {
    	    List<User> users = userService.getAllUsers();
    	    request.setAttribute("users", users);
    	    request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
    	}

    	private void viewUser(HttpServletRequest request, HttpServletResponse response) 
    	    throws ServletException, IOException {
    	    int userId = Integer.parseInt(request.getParameter("id"));
    	    User user = userService.getUserById(userId);
    	    request.setAttribute("user", user);
    	    request.getRequestDispatcher("/admin/user-details.jsp").forward(request, response);
    	}

    	private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
    	    throws ServletException, IOException {
    	    int userId = Integer.parseInt(request.getParameter("id"));
    	    User user = userService.getUserById(userId);
    	    request.setAttribute("user", user);
    	    request.getRequestDispatcher("/admin/edit-user.jsp").forward(request, response);
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
}