# Getting started with the Team Management API

This is the guide to help you quickly set up, run, and interact with the Team management API, which provides functionalities to manage teams in the system. The API supports CRUD operations, enabling clients to retrieve, create, update, and delete team entities.

--- 

# Table of Contents

1. **Getting started**
   
   - [Requirements](#requirements)
   - [Setup Instructions](#setup-instructions)
     - [Clone the Repository](#1-clone-the-repository)
     - [Configure the Database](#2-configure-the-database)
     - [Build the Project](#3-build-the-project)
     - [Run the Application](#4-run-the-application)
   - [Database Configuration](#2-Database-configuration)
   - [SendGrid configuration](#2-SendGrid-configuration)

2. **Employee management endpoints**
   
   - [Get All Employees](#get-all-employees)
   - [Add New Employee](#add-new-employee)
   - [Update Employee](#update-employee)
   - [Delete Employee](#delete-employee)

3. **Team management endpoints**
   
   - [Get All Teams](#get-all-teams)
   - [Add New Team](#add-new-team)
   - [Update Team](#update-team)
   - [Delete Team](#delete-team)

4. **Other functionality**
   
   - [Download employees summary](#download-employee-summary)
   - [Send email about add employee to his new team](#-2-Send-email-about-add-employee-to-hhis-new-team)

5. **Deployment**
   
   - [Deployment](#deployment)

---

## Requirements

Before starting, ensure you have the following installed on your system:

- **Java SDK 17**

- Maven 3.x (for dependency management and building the project)

- MySQL/PostgreSQL or any compatible database

- A REST client (e.g., cURL, Postman) for testing the API

- IDE (e.g., IntelliJ IDEA Ultimate Edition) for development purposes

- Spring Boot, Jakarta EE for the application environment

---

## Setup Instructions

### 1. Clone the repository

Clone the API repository to your local development environment:

```shell
git clone https://github.com/Nisser111/team-menagement-app.git
cd team-management-api/backend
```

### 2. Configure the database

- Create a new database `menagement_system` by [following instructions](#2-Database-Configuration).

- Update the database configurations in your `application.properties` 
  file located at `src/main/resources/`. Example:

```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/team_management
  spring.datasource.username=<your_username>
  spring.datasource.password=<your_password>
  spring.jpa.hibernate.ddl-auto=update
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3. Build the project

Use Maven to build the project:

```shell
mvn clean install
```

### 4. Run the application

Start the application using the following command:

```shell
mvn spring-boot:run
```

The application will run on the default port **8080** unless otherwise specified in the `application.properties`.

---

## Database Configuration

The **Management System** API interacts with a MySQL database. Below is the database schema and example data to set up the database.

---

#### Database Name

`management_system`

---

Import database script: [Database-run-script](https://github.com/Nisser111/team-menagement-app/blob/main/utils/database-run-script.sql)

---

#### Tables Overview

1. **`teams` Table**
   
   - Stores information about teams within the organization.
   
   **Schema**:

| Column name | Data type     | Constraints                 |
| ----------- | ------------- | --------------------------- |
| `ID`        | `int`         | Primary key, Auto increment |
| `name`      | `varchar(75)` | Not null                    |



3. **`employees` Table**
   
   - Stores information about employees and their association with teams.
   
   **Schema**:

| Column name  | Data type      | Constraints                        |
| ------------ | -------------- | ---------------------------------- |
| `ID`         | `int`          | Primary key, Auto increment        |
| `first_name` | `varchar(50)`  | Not null                           |
| `last_name`  | `varchar(50)`  | Not null                           |
| `email`      | `varchar(100)` | Not null, Unique                   |
| `phone`      | `varchar(15)`  | Optional                           |
| `hire_date`  | `date`         | Not null                           |
| `role`       | `varchar(50)`  | Not null                           |
| `team_id`    | `int`          | Foreign key references `teams(ID)` |


   **Relationships**:
   
   - `team_id`: Foreign key referencing the `teams` table. If a team is deleted, associated employees will also be removed (`ON DELETE CASCADE`).

---

#### Example Data

**Teams Table**:

| `ID` | `name`           |
| ---- | ---------------- |
| 1    | Development Team |
| 2    | Marketing Team   |
| 3    | Sales Team       |
| 4    | HR Team          |
| 5    | Design Team      |

**Employees Table**:

| `ID` | `first_name` | `last_name` | `email`                   | `phone`      | `hire_date` | `role`               | `team_id` |
| ---- | ------------ | ----------- | ------------------------- | ------------ | ----------- | -------------------- | --------- |
| 1    | John         | Doe         | john.doe@example.com      | 123-456-7890 | 2023-01-15  | Developer            | 1         |
| 2    | Jane         | Smith       | jane.smith@example.com    | 234-567-8901 | 2023-02-20  | Marketing Specialist | 2         |
| 3    | Emily        | Johnson     | emily.johnson@example.com | 345-678-9012 | 2023-03-10  | Sales Associate      | 3         |
| 4    | Michael      | Brown       | michael.brown@example.com | 456-789-0123 | 2023-04-05  | HR Manager           | 4         |
| 5    | Sarah        | Davis       | sarah.davis@example.com   | 567-890-1234 | 2023-05-12  | UI/UX Designer       | 5         |

---

#### Admin User

An administrator user is created for managing the database. Below are its credentials:

- **Username**: `admin`
- **Authentication**: MySQL Native Password
- **Privileges**: Full privileges across all tables with no restrictions.

```sql
CREATE USER 'admin'@'%' IDENTIFIED BY'QEC8u';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%';
```

---
## SendGrid configuration

The API provides functionality to send an email notification when employee is assigning to his/her new team. It integrates **SendGrid API** to manage mailing. There is guid how to configure this service. 

---
### 1. Get API

Register or log in to your account in **[sendgrid.com](https://sendgrid.com)** service. Get your API key and set them in environment variable.

```
SENDGRID_API_KEY=your api key
```
### 2. Configure the mail template.

Follow the instruction from the service website.  Next, copy the template id and add as environment variable.

```
MAIL_TEMPLATE_ID=the template ID
```

Make your own mail template or copy code from this file [mail-template.html](../utils/mail-template.html). If you chose to make your own you would include this properties:

```json
{
    "employee_name": "John",
    "team_name": "Development Team"
}
```

![[mail-template-preview.png]]

Set sender mail in environment variables. You should enter the mail configurated on you SendGrid account. 

```
SENDER_EMAIL=your-mail@example.com
```
### 3. Integrate your API with external API

Go to *Integration Guide* tab and follow step-by-step guide there to connect your app with the SendGrid. At the end verify connection by sending any request. 

---

# Get all Employees

### Endpoint

`GET /employees`

### Description

Retrieves a list of all employees from the system. Returns the full details of all employees stored in the database.

---

### Request

#### Method: `GET`

#### URL: `/employees`

#### Headers

No specific headers are required for this request. Optional headers may include:

- **Content-Type**: `application/json`

#### Query Parameters

This API does not require or utilize any query parameters.

#### Request Body

None. This API endpoint does not accept a body for the `GET` request.

---

### Response

#### Status Codes:

4. **200 OK**
   
   - **Description**: The request was successful, and the server has returned the list of all employees.
   
   - **Response Body Example** (JSON):

```json
 [
   {
     "id": 1,
     "firstName": "John",
     "lastName": "Doe",
     "email": "john.doe@example.com",
     "phone": "+123456789",
     "hireDate": "2022-01-15",
     "role": "Developer",
     "teamId": 101
   },
   {
     "id": 2,
     "firstName": "Jane",
     "lastName": "Smith",
     "email": "jane.smith@example.com",
     "phone": "+987654321",
     "hireDate": "2021-11-01",
     "role": "Manager",
     "teamId": 102
   }
 ]
```

5. **500 Internal Server Error**
   
   - **Description**: An unexpected error occurred on the server while retrieving employees.
   
   - **Response Body Example** (JSON):

```json
 {
   "error": "Unexpected error occurred."
 }
```

---

### CURL Example

```shell
curl -X GET http://localhost:8080/employees
```

---
# Add New Employee

### Endpoint

`POST /employees`

---

### Description

This endpoint allows the creation of a new employee in the system. It validates the provided details such as name, email, phone, hire date, role, and team ID before ersisting the employee in the database. The email must be unique.

---

### Request

#### Method: `POST`

#### URL: `/employees`

#### Headers

- **Content-Type**: `application/json`

#### Request Body (JSON Object)

A `JSON` object representing the new employee's details. All fields are required.

#### Fields

| Field Name | Type     | Required | Validation                                                                            | Description                                |
| ---------- | -------- | -------- | ------------------------------------------------------------------------------------- | ------------------------------------------ |
| firstName  | `string` | Yes      | - 2 to 50 characters long<br/>- Cannot be blank                                       | The first name of the employee             |
| lastName   | `string` | Yes      | - 2 to 50 characters long<br>- Cannot be blank                                        | The last name of the employee              |
| email      | `string` | Yes      | - Must follow a valid email format<br>- Cannot be blank<br>- Must be <br>unique       | The email address of the employee          |
| phone      | `string` | Yes      | - Must follow a valid phone number pattern (e.g., "+123...")<br>- <br>Cannot be blank | The phone number of the employee           |
| hireDate   | `string` | Yes      | - Must be a past or present date<br>- Cannot be blank                                 | The date the employee was hired            |
| role       | `string` | Yes      | - Cannot be blank                                                                     | The role or position of the employee       |
| teamId     | `int`    | Yes      | - Must be a positive number<br>- Cannot be blank                                      | The ID of the team the employee belongs to |

#### Example Request Body

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+123456789",
  "hireDate": "2022-01-15",
  "role": "Developer",
  "teamId": 101
}
```

---

### Response

#### Status Codes

6. **200 OK**
   
   - **Description**: The employee was successfully created.
   
   - **Response Body Example**:

```json
 {
   "message": "New employee has been added successfully."
 }
```

7. **400 Bad Request**
   
   - **Description**: The request contains invalid data, such as a duplicate email, nvalid fields, or missing required fields.
   
   - **Response Body Example**:

```json
 {
   "error": "Email is already in use."
 }
```

8. **500 Internal Server Error**
   
   - **Description**: An unexpected error occurred while processing the request.
   
   - **Response Body Example**:

```json
 {
   "error": "Unexpected error occurred."
 }
```

### Validation Rules

- **Email Validation**: Ensures email uniqueness and valid format.

- **Phone Number Validation**: The phone number must match predefined patterns.

- **Date Validation**: The hire date must not be in the future.

- **Team ID**: Team ID must be a positive integer.

---

### CURL Example

```shell
curl -X POST http://localhost:8080/employees \
-H "Content-Type: application/json" \
-d '{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+123456789",
  "hireDate": "2022-01-15",
  "role": "Developer",
  "teamId": 101
}'
```

---

# Update Employee

### Endpoint

`PATCH /employees/{id}`

---

### Description

This endpoint allows updating an existing employee's details. Only the fields provided in the request body will be updated. If a field is not 
included in the request body, it will not be modified.

---

### Request

#### Method: `PATCH`

#### URL: `/employees/{id}`

#### Path Parameters

| Parameter | Type  | Required | Description                                          |
| --------- | ----- | -------- | ---------------------------------------------------- |
| `id`      | `int` | Yes      | The unique identifier of the employee to be updated. |

#### Headers

- **Content-Type**: `application/json`

#### Request Body (JSON Object)

A `JSON` object containing one or more fields to be updated. All fields are optional and only provided fields will be updated.

#### Fields

| Field Name | Type     | Required | Validation                                                                            | Description                                   |
| ---------- | -------- | -------- | ------------------------------------------------------------------------------------- | --------------------------------------------- |
| firstName  | `string` | Optional | - 2 to 50 characters long<br>- Cannot be blank                                        | The updated first name of the employee.       |
| lastName   | `string` | Optional | - 2 to 50 characters long<br>- Cannot be blank                                        | The updated last name of the employee.        |
| email      | `string` | Optional | - Must follow a valid email format<br>- Cannot be blank<br>- Must be <br>unique       | The updated email address of the employee.    |
| phone      | `string` | Optional | - Must follow a valid phone number pattern (e.g., "+123...")<br>- <br>Cannot be blank | The updated phone number of the employee.     |
| hireDate   | `string` | Optional | - Must be a past or present date                                                      | The updated hire date of the employee.        |
| role       | `string` | Optional | - Cannot be blank                                                                     | The updated role or position of the employee. |
| teamId     | `int`    | Optional | - Must be a positive number                                                           | The updated team ID of the employee.          |

#### Example Request Body

```json
{
  "firstName": "John",
  "email": "john.new@example.com",
  "role": "Senior Developer"
}
```

---

### Response

#### Status Codes

9. **200 OK**
   
   - **Description**: The employee was successfully updated.
   
   - **Response Body Example**:

```json
{
  "message": "Employee updated successfully."
}
```

10. **400 Bad Request**
   
   - **Description**: The request contains invalid data, such as a duplicate email, invalid fields, or missing required fields.
   
   - **Response Body Example**:

```json
{
  "error": "Email is already in use."
}
```

11. **404 Not Found**
   
   - **Description**: No employee exists with the specified ID.
   
   - **Response Body Example**:

```json
 {
   "error": "Employee not found."
 }
```

12. **500 Internal Server Error**
   
   - **Description**: An unexpected error occurred while processing the request.
   
   - **Response Body Example**:

```json
 {
   "error": "An unexpected error occurred."
 }
```

### Validation Rules

- **Email Validation**: Ensures any updated email address is unique in the system and matches the valid email format.

- **Phone Number Validation**: The phone number must match predefined patterns.

- **Date Validation**: The hire date (if provided) must not be in the future.

- **Team ID**: The team ID must be a positive integer.

---

### **CURL Example**

```shell
curl -X PATCH http://localhost:8080/employees/1 \
-H "Content-Type: application/json" \
-d '{
  "firstName": "John",
  "email": "john.new@example.com",
  "role": "Senior Developer"
}'
```

---

# Delete Employee

### Endpoint

`DELETE /employees/{id}`

---

### Description

This endpoint allows deleting an employee from the system using their unique ID. If he employee is not found, an appropriate error message is returned.

---

### Request

#### Method: `DELETE`

#### URL: `/employees/{id}`

#### Path Parameters

| Parameter | Type  | Required | Description                                      |
| --------- | ----- | -------- | ------------------------------------------------ |
| `id`      | `int` | Yes      | The unique identifier of the employee to delete. |

#### Headers

No specific headers are required for this request. Optional headers may include:

- **Content-Type**: `application/json`

#### Request Body

None.

---

### Response

#### Status Codes

13. **200 OK**
   
   - **Description**: The employee was successfully 
     deleted.
   
   - **Response Body Example**:

```json
{
  "message": "Employee with ID 1 has been deleted."
}
```

14. **404 Not Found**
   
   - **Description**: The employee with the specified ID 
     does not exist in the system.
   
   - **Response Body Example**:

```json
 {
   "error": "Employee with ID 1 not found."
 }
```

---

### CURL Example

```shell
curl -X DELETE http://localhost:8080/employees/1
```

# Get all Teams

### Endpoint

`GET /teams`

---

### Description

This endpoint retrieves a list of all teams available in the system. Each team includes details such as its unique identifier (`id`) and name.

---

### Request

#### Method: `GET`

#### URL: `/teams`

#### Headers

No specific headers are required for this request. Optional headers may include:

- **Content-Type**: `application/json`

---

### Response

#### Status Codes

15. **200 OK**
   
   - **Description**: Returns a list of all teams in the 
     system.
   
   - **Response Body Example**:

```json
[
    {
        "id": 1,
        "name": "Development Team"
    },
    {
        "id": 2,
        "name": "Marketing Team"
    },
    {
        "id": 3,
        "name": "Sales Team"
    }
]
```

16. 500 Internal Server Error
   
   - **Description**: An unexpected server error occurred 
     while processing the request.
   
   - **Response Body Example**:

```json
{
  "error": "An unexpected error occurred."
}
```

---

### CURL Example

```shell
curl -X GET http://localhost:8080/teams
```

---

# Add new Team

### Endpoint

`POST /teams`

---

### Description

This endpoint allows the addition of a new team to the system. The team name is required and must meet the validation constraints. Upon successful creation, the team is saved to the database and a confirmation message is returned.

---

### Request

#### Method: `POST`

#### URL: `/teams`

#### Headers

- **Content-Type**: `application/json`

#### Request Body (JSON Object)

A `JSON` object representing the new team's details. All fields are required.

#### Fields

| Field  | Type   | Required | Validation Constraints                          |
| ------ | ------ | -------- | ----------------------------------------------- |
| `name` | String | Yes      | Must be between 2 and 50 characters, not blank. |

##### Example Request Body

```json
{
  "name": "Development Team"
}
```

---

### Response

#### Status Codes

17. **200 OK**
   
   - **Description**: The team was successfully added.
   
   - **Response Body Example**:

```json
{
  "message": "New team has been added successfully."
}
```

18. **400 Bad Request**
   
   - **Description**: The request body contains invalid or missing data. Validation errors will be returned in detail.
   
   - **Response Body Example**:

```json
{
  "error": "Team name is required"
}
```

19. **500 Internal Server Error**
   
   - **Description**: An unexpected server error occurred while processing the request.
   
   - **Response Body Example**:

```json
{
  "error": "Unexpected error occurred. Failed to add the new team."
}
```

---

### CURL Example

```shell
curl -X POST http://localhost:8080/teams \
-H "Content-Type: application/json" \
-d '{
  "name": "Development Team"
}'
```

---

# Update Team

### Endpoint

`PATCH /teams/{id}`

---

### Description

This endpoint allows an existing team's name to be updated based on its unique identifier (`id`). If the team with the provided `id` does not exist, an appropriate error response is returned.

---

### Request

#### Method: `PATCH`

#### URL: `/teams/{id}`

#### Path Parameters

| Parameter | Type  | Required | Description                                      |
| --------- | ----- | -------- | ------------------------------------------------ |
| `id`      | `int` | Yes      | The unique identifier of the team to be updated. |

#### Headers

- **Content-Type**: `application/json`

#### Request Body (JSON Object)

A `JSON` object representing the new employee's details. All fields are required.

#### Fields

| Field  | Type   | Required | Validation Constraints                          |
| ------ | ------ | -------- | ----------------------------------------------- |
| `name` | String | Yes      | Must be between 2 and 50 characters, not blank. |

##### Example Request Body

```json
{
  "name": "Development Teame"
}
```

---

### Response

#### Status Codes

20. **200 OK**
   
   - **Description**: The team was successfully updated.
   
   - **Response Body Example**:

```json
{
  "message": "Development Team has been updated to New Team Name."
}
```

21. **404 Not Found**
   
   - **Description**: The team with the specified ID does 
     not exist.
   
   - **Response Body Example**:

```json
{
  "error": "Team with ID 1 not found."
}
```

22. **400 Bad Request**
   
   - **Description**: The provided data is invalid or the 
     update operation failed.
   
   - **Response Body Example**:

```json
{
  "error": "Failed to update the team with ID 1."
}
```

---

### CURL Example

```shell
curl -X PATCH http://localhost:8080/teams/1 \
-H "Content-Type: application/json" \
-d '{
  "name": "Development Team"
}'
```

---

# Delete Team

### Endpoint

`DELETE /teams/{id}`

---

### Description

This endpoint allows the deletion of an existing team from the system based on its unique identifier (`id`). If the team is successfully deleted, a confirmation message is returned. If the team does not exist or the deletion fails, an appropriate error response is returned.

---

### Request

#### Method: `DELETE`

#### URL: `/teams/{id}`

#### Path Parameters

| Parameter | Type  | Required | Description                                      |
| --------- | ----- | -------- | ------------------------------------------------ |
| `id`      | `int` | Yes      | The unique identifier of the team to be deleted. |

#### Headers

No specific headers are required for this request. Optional headers may include:

- **Content-Type**: `application/json`

---

### Response

#### Status Codes

23. **200 OK**
   
   - **Description**: The team was successfully deleted.
   
   - **Response Body Example**:

```json
{
  "message": "Development Team has been deleted."
}
```

24. **404 Not Found**
   
   - **Description**: The team with the specified ID does 
     not exist.
   
   - **Response Body Example**:

```json
{
  "error": "Team with ID 10 not found."
}
```

25. **500 Internal Server Error**
   
   - **Description**: An unexpected server error occurred 
     during the deletion process.
   
   - **Response Body Example**:

```json
{
  "error": "Unexpected error occurred while deleting the team."
}
```

---

### CURL Example

```shell
curl -X DELETE http://localhost:8080/teams/1
```

---

# Download Employee Summary

### Endpoint

`GET /summary/download`

### Description

This endpoint generates and returns an Excel file containing a summary of all employees in the system. The Excel sheet includes the following columns:

- **First Name** – Employee's first name.
- **Last Name** – Employee's last name.
- **Email** – Employee's email address.
- **Phone** – Contact number of the employee.
- **Hire Date** – Date when the employee was hired.
- **Role** – Employee's job role.
- **Team Name** – Name of the team the employee belongs to.

---

### Request

#### Method: `GET`

#### URL: `/summary/download`

#### Headers

| Header Name | Required | Description                                                                                                                                                                                 |
| ----------- | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Accept      | Yes      | Must be set to `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet` to indicate an Excel file request. Must be set to `application/json` to handle error json type response. |

#### Request Body

None. This API does not accept any request body.

### Response

#### Status Codes

26. **200 OK**
   
   - **Description**: The request was successful.
   
   - **Response Body Example (Binary file download)**:
   
          The response body contains the binary Excel file (`.xlsx`).

27. **500 Internal Server Error**
   
   - **Description**: An unexpected error occurred while generating the summary file.
   
   - **Response Body Example**:

```json
{
    "error": "Error during generating summary."
}
```

- **Response Headers**
  
  - **Content-Type**: `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet` or `application/json`
  - **Content-Disposition**: `attachment; filename="summary.xlsx"`

---

#### CURL Example

```bash
curl -X GET http://localhost:8080/summary/download \
-H "Accept: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/json" \
-o summary.xlsx 
```

 ---

# Send email about add employee to his new team

### Endpoint

`POST /email/send`

---

### Description

This endpoint allows option to send an email notification to employee, when he/she was assigned to new team. The endpoint require three params: employee email, name and his/her new team. If the mail was send successfully, a confirmation message is returned. Otherwise error response is returned. 

---

### Request

#### Method: `POST`

#### URL: `/email/send?toEmail={email}&employee_name={name}&team_name={team name}`

#### Path Parameters

| Parameter       | Type     | Required | Description                                           |
| --------------- | -------- | -------- | ----------------------------------------------------- |
| `toEmail`       | `string` | Yes      | Employee email, which is reciver of the notification. |
| `employee_name` | `string` | Yes      | Employee name, which appears in message.              |
| `team_name`     | `string` | Yes      | Employee's new team name.                             |

#### Headers

No specific headers are required for this request. 

---

### Response

#### Status Codes

28. **200 OK**
   
   - **Description**: The mail message was sent successfully.
   
   - **Response Body Example**:

```json
{
  "message": "The message has been sent successfully."
}
```

29. **500 Internal Server Error**
   
   - **Description**: An unexpected server error occurred during the mail sending.
   
   - **Response Body Example**:

```json
{
  "error": "Error during sending an email."
}
```

---

### CURL Example

```shell
curl -X POST http://localhost:8080/email/send?toEmail=name@example.com&employee_name=adam&team_name=Development Team
```

---
## **Deployment**

To deploy the application:

30. Package it into a JAR file:

```shell
mvn package
```

The generated JAR file will be under the `target/` directory.

31. Run the JAR file:

```shell
java -jar target/team-management-api.jar
```

32. Ensure the application is connected to a production-ready database 
   with proper configurations.
