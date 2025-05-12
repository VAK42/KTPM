# Store Inventory Management Desktop Application
  
## **I - Title**
  
Store Inventory Management Desktop Application
  
## **II - Features**
  
- User Login System & Reset Password Ability (Admin)
- Product & Category Management (CRUD)
- Import Stock (Restocking)
- Export Stock (Sales Tracking)
- Live Product Quantity Updates
- Report Generation
  
## **III - Database Structure**
  
### 1. User
  
Manages Authorization
  
| Field      | Type    | Description               |
|------------|---------|---------------------------|
| userid     | UUID    | Unique Identifier         |
| username   | String  | Username                  |
| password   | String  | Encrypted Password        |
| email      | String  | User's Contact Email      |
| role       | String  | User Role (admin/user)    |
  
---
  
### 2. Category
  
Groups Products For Better Management
  
| Field        | Type   | Description              |
|--------------|--------|--------------------------|
| categoryid   | UUID   | Unique Category ID       |
| name         | String | Category Name            |
  
---
  
### 3. Product
  
Main Entity For Inventory Tracking
  
| Field        | Type     | Description                         |
|--------------|----------|-------------------------------------|
| pdid         | UUID     | Product ID                          |
| pdname       | String   | Product Name                        |
| pdprice      | Decimal  | Unit Price                          |
| categoryid   | UUID     | Foreign Key To Category             |
| pdinfo       | String   | Product Description/Info            |
| pdquantity   | Integer  | Quantity In Stock                   |
  
---
  
### 4. Import
  
Tracks Incoming Product Stock (Restocking)
  
| Field       | Type     | Description                       |
|-------------|----------|-----------------------------------|
| ipid        | UUID     | Import ID                         |
| pdid        | UUID     | Product ID                        |
| pdprice     | Decimal  | Purchase Price At Import          |
| pdquantity  | Integer  | Quantity Imported                 |
| date        | DateTime | Date Of Import                    |
  
---
  
### 5. Export
  
Tracks Outgoing Stock (Sales)
  
| Field         | Type     | Description                         |
|---------------|----------|-------------------------------------|
| epid          | UUID     | Export ID                           |
| pdid          | UUID     | Product ID                          |
| pdprice       | Decimal  | Selling Price At Export             |
| pdquantity    | Integer  | Quantity Exported                   |
| pdtotalprice  | Decimal  | Total Revenue From This Export      |
| userid        | UUID     | User Who Processed The Export       |
| date          | DateTime | Date Of Export                      |
  
---
  
### 6. Report
  
User-created Custom Reports
  
| Field      | Type   | Description                     |
|------------|--------|---------------------------------|
| reportid   | UUID   | Unique Report ID                |
| userid     | UUID   | Creator User ID                 |
| rpname     | String | Report Title                    |
| rpinfo     | String | Report Details                  |
  
---

## **IV - Project Structure**
  
```plaintext
oop/

├── .mvn/wrapper/        # Maven Wrapper
│   ├── maven-wrapper.jar
│   └── maven-wrapper.properties
│
├── mvnw                 # Unix Shell Script To Run Maven
├── mvnw.cmd             # Windows Batch Script For Maven
├── pom.xml              # Maven Project Configuration
├── VAK.sql              # SQL Schema For Database Setup
├── README.md            # Project Documentation
├── .gitignore           # Ignore Rules
│
└── src/main/
    ├── java/
    │   ├── controller/    # JavaFX Controllers (UI Logic)
    │   ├── dao/           # DAO Classes For DB Operations
    │   ├── model/         # Entity Classes (JPA Models)
    │   ├── service/       # Business Logic Layer
    │   ├── util/          # Utilities
    │   ├── view/          # JavaFX UI Initialization
    │   └── Main.java      # Application Entry Point
    │
    └── resources/
        ├── META-INF/
        │   └── persistence.xml  # JPA Configuration
        └── FXML/                # JavaFX UI Layouts
```
  
## **V - Used Technologies**

<img src="https://skillicons.dev/icons?i=java,hibernate,maven,postgres,idea" />
  
OpenJDK 24

JavaFX

JPA + Hibernate (ORM)

PostgreSQL

MVC + DAO/Service Architecture

## **VI - Getting Started**

1. Clone The Repository
2. Set Up The Database (SQL Schema Provided In VAK.sql)
3. Configure Database Settings In persistence.xml
4. Open Project In Your IDE (*JetBrains IntelliJ IDEA* Recommended)
5. Type Command: *.\mvnw.cmd javafx:run* To Run Application

## **VII - Team Members**
  
*Vu Anh Kiet* - VAK42 (*) 
  
*Tran Ha Quang* - Babiboyy55 
  
*Pham Thi Minh Ngoc* - mcongie
