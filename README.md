# TestCaseManagementService
A Spring Boot-based REST API for managing test cases. This service allows users to create, retrieve, update, and delete test cases stored in MongoDB.


#Technologies Used
Java 8+
Spring Boot (Backend Framework)
MongoDB (NoSQL Database)
Spring Data MongoDB (Database Access)
Swagger/OpenAPI (API Documentation)
JUnit 5 & Mockito (Testing Framework)

#Step 2: Start MongoDB Locally
Make sure MongoDB is installed and running on your system.

If MongoDB is not installed, download it from: MongoDB Download.

Start MongoDB service:
sh
mongod --dbpath /path/to/data/directory
Configure MongoDB Connection
Modify application.properties (or application.yml) to match your MongoDB settings.
application.properties
spring.data.mongodb.uri=mongodb://localhost:27017/testcasemanagement
spring.data.mongodb.database=testcasemanagement
server.port=8080

Build and Run the Application
mvn clean install
mvn spring-boot:run
The application will start at:
👉 http://localhost:8080

API Endpoints
HTTP Method	   Endpoint	                    Description
GET 	      /api/testcases	            Get all test cases (supports pagination & filtering)
GET	          /api/testcases/{id}	        Get test case by ID
POST	      /api/testcases	            Create a new test case
PUT	          /api/testcases/{id}	        Update a test case
DELETE	      /api/testcases/{id}	        Delete a test case

#Testing Instructions
Run unit tests and integration tests using:
mvn test

✅ Expected output:

Tests run: 5, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS

📊 Sample Test Data (MongoDB)

🔹 Step 1: Open MongoDB Shell:
mongosh

🔹Step 2: Insert Sample Data

use testcasemanagement;

db.testcases.insertMany([
{
title: "Login Test",
description: "Valid login scenario",
status: "PENDING",
priority: "HIGH",
createdAt: new Date(),
updatedAt: new Date()
},
{
title: "Signup Test",
description: "Valid signup scenario",
status: "IN_PROGRESS",
priority: "MEDIUM",
createdAt: new Date(),
updatedAt: new Date()
}
]);

🎯 Design Assumptions & Trade-offs

✔ Scalability: MongoDB indexing on status and priority helps optimize query performance.

✔ DTOs (Data Transfer Objects): Used to separate database models from API requests.

✔ Error Handling: Custom exceptions for invalid requests and missing resources.

✔ Clean Code & Design Patterns: Organized into Controller, Service, and Repository layers for maintainability.


