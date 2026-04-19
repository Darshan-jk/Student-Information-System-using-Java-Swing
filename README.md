# 🎓 Student Portal Management System

A **Java Swing + MySQL based desktop application** that allows users to register, login, and manage their student profile with image upload and editing features.

---

## 🚀 Features

* 🔐 User Authentication (Login & Signup)
* 🧑 Student Profile Management
* 🖼️ Profile Image Upload & Display
* ✏️ Edit Profile (Username, Name, DOB, Phone, Image)
* 🚫 Duplicate Username Prevention
* 🧾 MySQL Database Integration
* 🔄 Real-time UI Updates

---

## 🛠️ Technologies Used

* **Java (Swing GUI)**
* **MySQL Database**
* **JDBC (Java Database Connectivity)**
* **NetBeans / IntelliJ IDE**

---

## 📂 Project Structure

```
Student-Portal/
│── LoginPage.java
│── SignupPage.java
│── Dashboard.java
│── DBConnection.java (optional)
│── README.md
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository

```
git clone https://github.com/your-username/student-portal.git
cd student-portal
```

---

### 2️⃣ Setup MySQL Database

Run the following SQL:

```sql
CREATE DATABASE student_portal;

USE student_portal;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    name VARCHAR(100),
    dob VARCHAR(20),
    phone VARCHAR(15),
    image LONGBLOB
);
```

---

### 3️⃣ Configure Database in Java

Update your DB credentials in code:

```java
DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/student_portal",
    "root",
    "your_password"
);
```

---

### 4️⃣ Add MySQL Connector

* Download MySQL Connector/J
* Add `.jar` file to project libraries

---

### 5️⃣ Run the Application

* Run `LoginPage.java`
* Signup → Login → Dashboard

---

## 🖥️ Screenshots

* Login Page
  ![Log In Screenshot](Screenshot1.PNG)
* Signup Page
  ![Sign Up Screenshot](Screenshot2.PNG)
* Dashboard with Profile Image
  ![Dashboard Screenshot](Screenshot3.PNG)

---

## 🔒 Future Improvements

* Password hashing (security)
* Email-based authentication
* Admin panel (view/manage users)
* Profile image cropping
* Deploy as web application (Spring Boot)

---

## 👨‍💻 Author

**Darshan JK**

---

## 📜 License

This project is for educational purposes.
