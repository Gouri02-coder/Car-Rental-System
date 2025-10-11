package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

@WebServlet("/UserCtrl")
public class UserCtrl extends HttpServlet {
    private UserDAO userDAO;
    
    public void init() {
        userDAO = new UserDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        System.out.println("Received action: " + action); // Debug line
        
        if ("register".equals(action)) {
            registerUser(request, response);
        } else if ("login".equals(action)) {
            loginUser(request, response);
        } else if ("logout".equals(action)) {
            logoutUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("logout".equals(action)) {
            logoutUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method not supported");
        }
    }
    
    private void registerUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get all parameters from the new registration form
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String licenseNumber = request.getParameter("licenseNumber");
            
            System.out.println("Registration attempt for: " + email); // Debug line
            
            // Validate required fields
            if (isEmpty(firstName) || isEmpty(lastName) || isEmpty(email) || isEmpty(password) || 
                isEmpty(phone) || isEmpty(address) || isEmpty(licenseNumber)) {
                request.setAttribute("errorMessage", "All fields are required!");
                request.getRequestDispatcher("/auth/register/UserRegistration.jsp").forward(request, response);
                return;
            }
            
            // Validate password match
            if (!password.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "Passwords do not match!");
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.setAttribute("licenseNumber", licenseNumber);
                request.getRequestDispatcher("/auth/register/UserRegistration.jsp").forward(request, response);
                return;
            }
            
            // Check if email already exists
            if (userDAO.isEmailExists(email)) {
                request.setAttribute("errorMessage", "Email already exists!");
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.setAttribute("licenseNumber", licenseNumber);
                request.getRequestDispatcher("/auth/register/UserRegistration.jsp").forward(request, response);
                return;
            }
            
            // Check if license number already exists
            if (userDAO.isLicenseExists(licenseNumber)) {
                request.setAttribute("errorMessage", "License number already registered!");
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.getRequestDispatcher("/auth/register/UserRegistration.jsp").forward(request, response);
                return;
            }
            
            // Create User object - combine first and last name for the 'name' field
            String fullName = firstName + " " + lastName;
            User user = new User();
            user.setName(fullName);
            user.setEmail(email);
            user.setPassword(password); // You should hash this in production
            user.setPhone(phone);
            user.setAddress(address);
            user.setLicenseNumber(licenseNumber);
            user.setRole("customer");
            
            if (userDAO.registerUser(user)) {
                request.setAttribute("successMessage", "Registration successful! Please login.");
                request.getRequestDispatcher("/auth/login/UserLogin.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed! Please try again.");
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.setAttribute("licenseNumber", licenseNumber);
                request.getRequestDispatcher("/auth/register/UserRegistration.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "System error: " + e.getMessage());
            request.getRequestDispatcher("/auth/register/UserRegistration.jsp").forward(request, response);
        }
    }
    
    private void loginUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        User user = userDAO.authenticateUser(email, password);
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());
            
            if ("admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.getRequestDispatcher("/auth/login/UserLogin.jsp").forward(request, response);
        }
    }
    
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
    
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}