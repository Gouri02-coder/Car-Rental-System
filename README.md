# 🚗 Car Rental System – Advanced Java Project

## 📘 Overview
The **Car Rental System** is a web-based application built using **Advanced Java (JSP, Servlets, JDBC)**.  
It allows users to rent cars online and enables admins to manage vehicles, users, and bookings efficiently.

---

## ⚙️ Technologies Used
- **Frontend:** HTML, CSS, JavaScript, JSP  
- **Backend:** Java Servlets, JDBC, JavaBeans  
- **Database:** MySQL  
- **Server:** Apache Tomcat  
- **IDE:** Eclipse / NetBeans  
- **Build Tool:** Maven (optional)

---

## 🧩 Modules

### 🔹 Admin Module
- Add, update, or delete car details  
- Manage customer bookings  
- Approve or cancel car rental requests  
- View rental history and payments

### 🔹 Customer Module
- Register and login  
- Browse and search available cars  
- Book cars for a specific date range  
- Make payments (simulated)  
- View booking history

### 🔹 Authentication
- Secure login for both admin and users  
- Session management using `HttpSession`

---

## 💡 Features
- Real-time car availability tracking  
- Rent calculation based on car type and duration  
- Role-based dashboard (Admin/User)  
- MVC (Model-View-Controller) architecture  
- Form validation and error handling  
- Database persistence using JDBC  

---

## 🗄️ Database Structure

### `users` Table
| Column | Type | Description |
|---------|------|-------------|
| user_id | INT | Primary Key |
| name | VARCHAR | User full name |
| email | VARCHAR | Unique email |
| password | VARCHAR | Encrypted password |
| role | VARCHAR | 'admin' or 'user' |

### `cars` Table
| Column | Type | Description |
|---------|------|-------------|
| car_id | INT | Primary Key |
| model | VARCHAR | Car model |
| brand | VARCHAR | Car brand |
| price_per_day | DECIMAL | Daily rental price |
| status | VARCHAR | Available / Booked |

### `bookings` Table
| Column | Type | Description |
|---------|------|-------------|
| booking_id | INT | Primary Key |
| user_id | INT | Foreign Key (users) |
| car_id | INT | Foreign Key (cars) |
| start_date | DATE | Start date of rental |
| end_date | DATE | End date of rental |
| total_amount | DECIMAL | Total rent |
| status | VARCHAR | Confirmed / Cancelled |

---

## 🚀 How to Run

1. Install **JDK**, **MySQL**, and **Apache Tomcat**.  
2. Import the project into **Eclipse** or **NetBeans**.  
3. Create a MySQL database named `car_rental` and import the SQL script.  
4. Update DB credentials in `DBConnection.java`.  
5. Deploy the project on **Tomcat Server**.  
6. Access via browser:  
   - User: `http://localhost:8080/CarRentalSystem`  
   - Admin: `http://localhost:8080/CarRentalSystem/admin`

---

## 🔐 Default Login

**Admin**  
