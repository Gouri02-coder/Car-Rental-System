package model;

import java.sql.Timestamp;

public class SystemSettings {
    // Company Information
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;
    private String companyWebsite;
    
    // Business Settings
    private double taxRate; // e.g., 0.18 for 18%
    private String currency;
    private String timezone;
    private String dateFormat;
    
    // Rental Settings
    private int minRentalHours;
    private int maxRentalDays;
    private double lateReturnPenaltyRate; // per hour
    private double securityDeposit;
    private int maxCarsPerUser;
    
    // Pricing Settings
    private double baseDailyRate;
    private double weeklyDiscount; // e.g., 0.10 for 10%
    private double monthlyDiscount; // e.g., 0.20 for 20%
    private double cleaningFee;
    private double deliveryFee;
    
    // Notification Settings
    private boolean emailNotifications;
    private boolean smsNotifications;
    private String smsProvider;
    private String emailProvider;
    
    // Security Settings
    private int sessionTimeout; // in minutes
    private int maxLoginAttempts;
    private boolean requireStrongPassword;
    private boolean twoFactorAuth;
    
    // System Maintenance
    private boolean maintenanceMode;
    private String maintenanceMessage;
    private boolean allowRegistrations;
    private boolean allowBookings;
    
    // Audit Fields
    private Timestamp updatedAt;
    private int updatedBy; // Admin ID who last updated
    
    // Constructor
    public SystemSettings() {
        // Default values
        this.companyName = "Car Rental System";
        this.taxRate = 0.18;
        this.currency = "USD";
        this.minRentalHours = 4;
        this.maxRentalDays = 30;
        this.lateReturnPenaltyRate = 5.0;
        this.baseDailyRate = 50.0;
        this.emailNotifications = true;
        this.sessionTimeout = 30;
        this.maxLoginAttempts = 5;
        this.allowRegistrations = true;
        this.allowBookings = true;
    }
    
    // Business Logic Methods
    public double calculateTax(double amount) {
        return amount * taxRate;
    }
    
    public double calculateTotalWithTax(double amount) {
        return amount + calculateTax(amount);
    }
    
    public double calculateWeeklyRate(double dailyRate) {
        return (dailyRate * 7) * (1 - weeklyDiscount);
    }
    
    public double calculateMonthlyRate(double dailyRate) {
        return (dailyRate * 30) * (1 - monthlyDiscount);
    }
    
    public double calculateLatePenalty(int hoursLate) {
        return hoursLate * lateReturnPenaltyRate;
    }
    
    public boolean isWithinRentalLimit(int days) {
        return days >= minRentalHours / 24.0 && days <= maxRentalDays;
    }
    
    // Validation Methods
    public boolean isValidTaxRate() {
        return taxRate >= 0 && taxRate <= 1;
    }
    
    public boolean isValidDiscountRate(double discount) {
        return discount >= 0 && discount <= 1;
    }
    
    // Getters and Setters
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getCompanyAddress() { return companyAddress; }
    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }
    
    public String getCompanyPhone() { return companyPhone; }
    public void setCompanyPhone(String companyPhone) { this.companyPhone = companyPhone; }
    
    public String getCompanyEmail() { return companyEmail; }
    public void setCompanyEmail(String companyEmail) { this.companyEmail = companyEmail; }
    
    public String getCompanyWebsite() { return companyWebsite; }
    public void setCompanyWebsite(String companyWebsite) { this.companyWebsite = companyWebsite; }
    
    public double getTaxRate() { return taxRate; }
    public void setTaxRate(double taxRate) { this.taxRate = taxRate; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    
    public String getDateFormat() { return dateFormat; }
    public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat; }
    
    public int getMinRentalHours() { return minRentalHours; }
    public void setMinRentalHours(int minRentalHours) { this.minRentalHours = minRentalHours; }
    
    public int getMaxRentalDays() { return maxRentalDays; }
    public void setMaxRentalDays(int maxRentalDays) { this.maxRentalDays = maxRentalDays; }
    
    public double getLateReturnPenaltyRate() { return lateReturnPenaltyRate; }
    public void setLateReturnPenaltyRate(double lateReturnPenaltyRate) { this.lateReturnPenaltyRate = lateReturnPenaltyRate; }
    
    public double getSecurityDeposit() { return securityDeposit; }
    public void setSecurityDeposit(double securityDeposit) { this.securityDeposit = securityDeposit; }
    
    public int getMaxCarsPerUser() { return maxCarsPerUser; }
    public void setMaxCarsPerUser(int maxCarsPerUser) { this.maxCarsPerUser = maxCarsPerUser; }
    
    public double getBaseDailyRate() { return baseDailyRate; }
    public void setBaseDailyRate(double baseDailyRate) { this.baseDailyRate = baseDailyRate; }
    
    public double getWeeklyDiscount() { return weeklyDiscount; }
    public void setWeeklyDiscount(double weeklyDiscount) { this.weeklyDiscount = weeklyDiscount; }
    
    public double getMonthlyDiscount() { return monthlyDiscount; }
    public void setMonthlyDiscount(double monthlyDiscount) { this.monthlyDiscount = monthlyDiscount; }
    
    public double getCleaningFee() { return cleaningFee; }
    public void setCleaningFee(double cleaningFee) { this.cleaningFee = cleaningFee; }
    
    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }
    
    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }
    
    public boolean isSmsNotifications() { return smsNotifications; }
    public void setSmsNotifications(boolean smsNotifications) { this.smsNotifications = smsNotifications; }
    
    public String getSmsProvider() { return smsProvider; }
    public void setSmsProvider(String smsProvider) { this.smsProvider = smsProvider; }
    
    public String getEmailProvider() { return emailProvider; }
    public void setEmailProvider(String emailProvider) { this.emailProvider = emailProvider; }
    
    public int getSessionTimeout() { return sessionTimeout; }
    public void setSessionTimeout(int sessionTimeout) { this.sessionTimeout = sessionTimeout; }
    
    public int getMaxLoginAttempts() { return maxLoginAttempts; }
    public void setMaxLoginAttempts(int maxLoginAttempts) { this.maxLoginAttempts = maxLoginAttempts; }
    
    public boolean isRequireStrongPassword() { return requireStrongPassword; }
    public void setRequireStrongPassword(boolean requireStrongPassword) { this.requireStrongPassword = requireStrongPassword; }
    
    public boolean isTwoFactorAuth() { return twoFactorAuth; }
    public void setTwoFactorAuth(boolean twoFactorAuth) { this.twoFactorAuth = twoFactorAuth; }
    
    public boolean isMaintenanceMode() { return maintenanceMode; }
    public void setMaintenanceMode(boolean maintenanceMode) { this.maintenanceMode = maintenanceMode; }
    
    public String getMaintenanceMessage() { return maintenanceMessage; }
    public void setMaintenanceMessage(String maintenanceMessage) { this.maintenanceMessage = maintenanceMessage; }
    
    public boolean isAllowRegistrations() { return allowRegistrations; }
    public void setAllowRegistrations(boolean allowRegistrations) { this.allowRegistrations = allowRegistrations; }
    
    public boolean isAllowBookings() { return allowBookings; }
    public void setAllowBookings(boolean allowBookings) { this.allowBookings = allowBookings; }
    
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    
    public int getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(int updatedBy) { this.updatedBy = updatedBy; }
}