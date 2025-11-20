package controller.Admin;

import services.ReportService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/reports")
public class AdminReportServlet extends HttpServlet {
    private ReportService reportService;
    
    @Override
    public void init() {
        this.reportService = new ReportService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");
        
        // Check if user is admin
        if (!"admin".equals(userRole)) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp?error=Access denied");
            return;
        }
        
        String action = request.getParameter("action");
        String type = request.getParameter("type");
        String format = request.getParameter("format");
        
        if (action == null) {
            action = "view";
        }
        
        try {
            switch (action) {
                case "view":
                    handleViewReport(request, response, type);
                    break;
                case "export":
                    handleExportReport(request, response, type, format);
                    break;
                case "summary":
                    handleSummaryReport(request, response);
                    break;
                default:
                    handleViewReport(request, response, "revenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp?error=Error generating report");
        }
    }
    
    private void handleViewReport(HttpServletRequest request, HttpServletResponse response, String type) 
            throws ServletException, IOException {
        
        if (type == null) {
            type = "revenue";
        }
        
        Map<String, Object> report;
        String jspPage;
        
        switch (type) {
            case "revenue":
                String period = request.getParameter("period");
                if (period == null) period = "monthly";
                report = reportService.getRevenueReport(period);
                jspPage = "/admin/reports/revenue-report.jsp";
                request.setAttribute("period", period);
                break;
                
            case "vehicles":
                report = reportService.getVehicleStatusReport();
                jspPage = "/admin/reports/vehicle-report.jsp";
                break;
                
            case "performance":
                report = reportService.getCarPerformanceReport();
                jspPage = "/admin/reports/performance-report.jsp";
                break;
                
            case "bookings":
                report = reportService.getBookingAnalysisReport();
                jspPage = "/admin/reports/booking-report.jsp";
                break;
                
            case "users":
                report = reportService.getUserActivityReport();
                jspPage = "/admin/reports/user-report.jsp";
                break;
                
            case "financial":
                report = reportService.getFinancialSummary();
                jspPage = "/admin/reports/financial-report.jsp";
                break;
                
            default:
                report = reportService.getRevenueReport("monthly");
                jspPage = "/admin/reports/revenue-report.jsp";
        }
        
        request.setAttribute("report", report);
        request.setAttribute("reportType", type);
        request.getRequestDispatcher(jspPage).forward(request, response);
    }
    
    private void handleExportReport(HttpServletRequest request, HttpServletResponse response, 
                                   String type, String format) throws IOException {
        
        if (format == null) format = "csv";
        if (type == null) type = "revenue";
        
        Map<String, Object> report;
        
        switch (type) {
            case "revenue":
                String period = request.getParameter("period");
                if (period == null) period = "monthly";
                report = reportService.getRevenueReport(period);
                break;
            case "vehicles":
                report = reportService.getVehicleStatusReport();
                break;
            case "performance":
                report = reportService.getCarPerformanceReport();
                break;
            case "bookings":
                report = reportService.getBookingAnalysisReport();
                break;
            case "users":
                report = reportService.getUserActivityReport();
                break;
            case "financial":
                report = reportService.getFinancialSummary();
                break;
            default:
                report = reportService.getRevenueReport("monthly");
        }
        
        String content;
        String contentType;
        String fileName;
        
        if ("csv".equals(format)) {
            content = reportService.generateCSVReport(report, type);
            contentType = "text/csv";
            fileName = type + "-report-" + System.currentTimeMillis() + ".csv";
        } else {
            // Default to CSV
            content = reportService.generateCSVReport(report, type);
            contentType = "text/csv";
            fileName = type + "-report-" + System.currentTimeMillis() + ".csv";
        }
        
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.getWriter().write(content);
    }
    
    private void handleSummaryReport(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Map<String, Object> revenueReport = reportService.getRevenueReport("monthly");
        Map<String, Object> vehicleReport = reportService.getVehicleStatusReport();
        Map<String, Object> bookingReport = reportService.getBookingAnalysisReport();
        Map<String, Object> financialReport = reportService.getFinancialSummary();
        
        request.setAttribute("revenueReport", revenueReport);
        request.setAttribute("vehicleReport", vehicleReport);
        request.setAttribute("bookingReport", bookingReport);
        request.setAttribute("financialReport", financialReport);
        
        request.getRequestDispatcher("/admin/reports/summary-report.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}