package controller.Admin;

import services.ReportService;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/reports")
public class AdminReportServlet extends HttpServlet {
    private ReportService reportService;
    
    @Override
    public void init() {
        reportService = new ReportService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String reportType = request.getParameter("type");
        
        if (reportType == null) {
            reportType = "dashboard";
        }
        
        switch (reportType) {
            case "revenue":
                generateRevenueReport(request, response);
                break;
            case "booking":
                generateBookingReport(request, response);
                break;
            case "car":
                generateCarReport(request, response);
                break;
            case "user":
                generateUserReport(request, response);
                break;
            case "analytics":
                generateBookingAnalytics(request, response);
                break;
            default:
                showDashboard(request, response);
        }
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Map<String, Object> dashboardStats = reportService.getDashboardStats();
        request.setAttribute("stats", dashboardStats);
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
    
    private void generateRevenueReport(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String period = request.getParameter("period");
        if (period == null) period = "monthly";
        
        Map<String, Object> revenueData = reportService.getRevenueReport(period);
        request.setAttribute("revenueData", revenueData);
        request.setAttribute("period", period);
        request.getRequestDispatcher("/admin/revenue-report.jsp").forward(request, response);
    }
    
    private void generateBookingReport(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        Map<String, Object> bookingData = reportService.getBookingReport(startDate, endDate);
        request.setAttribute("bookingData", bookingData);
        request.getRequestDispatcher("/admin/booking-report.jsp").forward(request, response);
    }
    
    private void generateCarReport(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Map<String, Object> carData = reportService.getCarUtilizationReport();
        request.setAttribute("carData", carData);
        request.getRequestDispatcher("/admin/car-report.jsp").forward(request, response);
    }
    
    private void generateUserReport(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Map<String, Object> userData = reportService.getUserActivityReport();
        request.setAttribute("userData", userData);
        request.getRequestDispatcher("/admin/user-report.jsp").forward(request, response);
    }
    
    private void generateBookingAnalytics(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String timeframe = request.getParameter("timeframe");
        if (timeframe == null) timeframe = "monthly";
        
        Map<String, Object> analyticsData = reportService.getBookingAnalytics(timeframe);
        request.setAttribute("analyticsData", analyticsData);
        request.setAttribute("timeframe", timeframe);
        request.getRequestDispatcher("/admin/booking-analytics.jsp").forward(request, response);
    }
}