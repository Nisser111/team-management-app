# Getting Started with the Team Management API

This is the guide to help you quickly set up, run, and interact with the Team anagement API, which provides functionalities to manage teams in the system. The API supports CRUD operations, enabling clients to retrieve, create, update, and delete team entities.

--- 

# Table of Contents

## Getting Started

1. [Requirements](#requirements)
2. [Setup Instructions](#setup-instructions)
   - [Clone Repository](#1-clone-the-repository)
   - [Configure Database](#2-configure-the-database)
   - [Build Project](#3-build-the-project)
   - [Run Application](#4-run-the-application)

## Database Configuration

1. [Database Overview](#database-configuration)
   - [Database Name](#database-name)
   - [Tables Overview](#tables-overview)
     - [Teams Table](#1-teams-table)
     - [Employees Table](#2-employees-table)
   - [Example Data](#example-data)
   - [Admin User](#admin-user)

## Response Structure

1. [Standard Response Format](#response-structure)
   - [Common Response Keys](#1-every-response-contains-the-following-keys)
   - [Localized Messages](#2-localized-messages)
   - [Error Handling](#3-error-handling)

## API Endpoints

### Employees

1. [Get All Employees](#get-all-employees)
   
   - [Endpoint Description](#description)
   - [Request Details](#request)
   - [Response Format](#response)
   - [CURL Example](#curl-example)

2. [Add New Employee](#add-new-employee)
   
   - [Endpoint Description](#description-1)
   - [Request Details](#request-1)
   - [Response Format](#response-1)
   - [Validation Rules](#validation-rules)
   - [CURL Example](#curl-example-1)

3. [Update Employee](#update-employee)
   
   - [Endpoint Description](#description-2)
   - [Request Details](#request-2)
   - [Response Format](#response-2)
   - [Validation Rules](#validation-rules-1)
   - [CURL Example](#curl-example-2)

4. [Delete Employee](#delete-employee)
   
   - [Endpoint Description](#description-3)
   - [Request Details](#request-3)
   - [Response Format](#response-3)
   - [CURL Example](#curl-example-3)

### Teams

1. [Get All Teams](#get-all-teams)
   
   - [Endpoint Description](#description-4)
   - [Request Details](#request-4)
   - [Response Format](#response-4)
   - [CURL Example](#curl-example-4)

2. [Add New Team](#add-new-team)
   
   - [Endpoint Description](#description-5)
   - [Request Details](#request-5)
   - [Response Format](#response-5)
   - [CURL Example](#curl-example-5)

3. [Update Team](#update-team)
   
   - [Endpoint Description](#description-6)
   - [Request Details](#request-6)
   - [Response Format](#response-6)
   - [CURL Example](#curl-example-6)

4. [Delete Team](#delete-team)
   
   - [Endpoint Description](#description-7)
   - [Request Details](#request-7)
   - [Response Format](#response-7)
   - [CURL Example](#curl-example-7)

## Deployment

1. [Deployment Instructions](#deployment)
   - [Package Application](#1-package-it-into-a-jar-file)
   - [Run Application](#2-run-the-jar-file)
   - [Database Configuration](#3-ensure-the-application-is-connected-to-a-production-ready-database)

---

## Requirements

Before starting, ensure you have the following installed on your system:

- **Java SDK 23**

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
   
   | Column Name | Data Type     | Constraints                 |
   | ----------- | ------------- | --------------------------- |
   | `ID`        | `int`         | Primary Key, Auto Increment |
   | `name`      | `varchar(75)` | Not Null                    |

2. **`employees` Table**
   
   - Stores information about employees and their association with teams.
   
   **Schema**:
   
   | Column Name  | Data Type      | Constraints                        |
   | ------------ | -------------- | ---------------------------------- |
   | `ID`         | `int`          | Primary Key, Auto Increment        |
   | `first_name` | `varchar(50)`  | Not Null                           |
   | `last_name`  | `varchar(50)`  | Not Null                           |
   | `email`      | `varchar(100)` | Not Null, Unique                   |
   | `phone`      | `varchar(15)`  | Optional                           |
   | `hire_date`  | `date`         | Not Null                           |
   | `role`       | `varchar(50)`  | Not Null                           |
   | `team_id`    | `int`          | Foreign Key References `teams(ID)` |
   
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

# Response structure

1. Every response contains the following keys:
   
   - `success` (Boolean): Indicates whether the request was successful (`true`) or not (`false`).
   
   - `message` (String): Describes the result of the operation or any error that occurred.
   
   - `data` (Object, optional): Contains details of the newly created employee (when successful).
   
   - `error` (String, optional): Captures any technical error details during a failure.

2. **Localized Messages:**
   
   - Success and error messages are localized in Polish where applicable, e.g., `"Pracownik John Doe został dodany."`.

3. **Error Handling:**
   
   - **404 Not Found**: Returned when the team with the specified ID doesn't exist.
   
   - **400 Bad Request**: Returned when the deletion operation fails despite the team existing.
   
   - **500 Internal Server Error**: Used for unexpected technical issues during the processing

---

# Get all Employees

### Endpoint

`GET /employees`

### Description

Retrieves a list of all employees from the system. Returns employee details inside a structured response body, including success flags, messages, and data.

---

### Request

#### Method: `GET`

#### URL: `/employees`

#### Headers

Headers are optional for this request.

- **Content-Type**: `application/json`

#### Query Parameters

This API does not require or utilize any query parameters.

#### Request Body

None. This API endpoint does not accept a body for the `GET` request.

---

### Response

#### Status Codes:

1. **200 OK**
   
   - **Description**: The request was successful, and the server has returned the list of all employees.
   
   - **Response Body Example** (JSON - Employees found):

```json
 {
  "success": true,
  "message": "Pracownicy zostali pomyślnie pobrani.",
  "data": [
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
}
```

- Response Body Example** (JSON - Employees found):

```json
{
  "success": true,
  "message": "Nie znaleziono pracowników.",
  "data": []
}
```



2. **500 Internal Server Error**
- **Description**: An unexpected error occurred on the server while retrieving employees.

- **Response Body Example** (JSON):

```json
{
  "success": false,
  "message": "Wystąpił nieoczekiwany błąd."
}
```

---

### CURL Example

```shell
curl -X GET http://localhost:8080/employees
```

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

Headers are required for this request.

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

1. **200 OK**
   
   - **Description**: The employee was successfully created.
   
   - **Response Body Example**:

```json
{
  "success": true,
  "message": "Pracownik John Doe został dodany.",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "+123456789",
    "hireDate": "2022-01-15",
    "role": "Developer",
    "teamId": 101
  }
}
```

2. **400 Bad Request**
   
   - **Description**: The request contains invalid data, such as a duplicate email, nvalid fields, or missing required fields.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Email is already in use."
}
```

```json
{
  "success": false,
  "message": "Hire date must not be in the future."
}
```

3. **500 Internal Server Error**
- **Description**: An unexpected error occurred while processing the request.

- **Response Body Example**:

```json
{
  "success": false,
  "message": "Wystąpił nieoczekiwany błąd."
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

This endpoint allows updating an existing employee's details. Only the fields provided in the request body will be updated. If a field is not included in the request body, it will not be modified.

---

### Request

#### Method: `PATCH`

#### URL: `/employees/{id}`

#### Path Parameters

| Parameter | Type  | Required | Description                                          |
| --------- | ----- | -------- | ---------------------------------------------------- |
| `id`      | `int` | Yes      | The unique identifier of the employee to be updated. |

#### Headers

Headers are required for this request.

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

1. **200 OK**
   
   - **Description**: The employee was successfully updated.
   
   - **Response Body Example**:

```json
{
  "success": true,
  "message": "Pracownik John Doe został pomyślnie zaktualizowany.",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.new@example.com",
    "phone": "+123456789",
    "hireDate": "2022-01-15",
    "role": "Senior Developer",
    "teamId": 101
  }
}
```

2. **400 Bad Request**
   
   - **Description**: The request contains invalid data, such as a duplicate email, invalid fields, or missing required fields.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Email is already in use."
}
```

```json
{
  "success": false,
  "message": "Hire date must not be in the future."
}
```

3. **404 Not Found**
   
   - **Description**: No employee exists with the specified ID.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Nie znaleziono pracownika o identyfikatorze 1."
}
```

4. **500 Internal Server Error**
   
   - **Description**: An unexpected error occurred while processing the request.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Wystąpił nieoczekiwany błąd."
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

Headers are optional for this request.

- **Content-Type**: `application/json`

#### Request Body

None.

---

### Response

#### Status Codes

1. **200 OK**
   
   - **Description**: The employee was successfully 
     deleted.
   
   - **Response Body Example**:

```json
{
  "success": true,
  "message": "Pracownik John Doe został usunięty.",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "+123456789",
    "hireDate": "2022-01-15",
    "role": "Developer",
    "teamId": 101
  }
}
```

2. **404 Not Found**
   
   - **Description**: The employee with the specified ID 
     does not exist in the system.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Nie znaleziono pracownika o identyfikatorze 1."
}
```

3. **500 Internal Server Error**
- **Description**: An unexpected error occurred while 
  processing the request.

- **Response Body Example**

```json
{
  "success": false,
  "message": "Wystąpił nieoczekiwany błąd."
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

This endpoint retrieves a list of all teams in the system. Each team includes details like its unique identifier (`id`) and name. If no teams are available, an appropriate message is returned. The response is structured and provides information about the success, message, and the team data.

---

### Request

#### Method: `GET`

#### URL: `/teams`

#### Headers

Headers are optional for this request.

- **Content-Type**: `application/json`

---

### Response

#### Status Codes

1. **200 OK**
   
   - **Description**: A list of all teams in the system is returned, or an indication that 
     no teams are available.
   
   - **Response Body Example** (teams found):

```json
{
  "success": true,
  "message": "Zespoły pobrane pomyślnie.",
  "data": [
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
}
```

- Response Body Example** (teams not found):

```json
{
  "success": true,
  "message": "Nie pobrano żadnych zespołów.",
  "data": []
}
```

2. 500 Internal Server Error
- **Description**: An unexpected server error occurred 
  while processing the request.

- **Response Body Example**:

```json
{
  "success": false,
  "message": "Wystąpił nieoczekiwany błąd podczas pobierania zespołów.",
  "error": "Error details (if available)"
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

- Headers are required for this request.
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

1. **200 OK**
   
   - **Description**: The team was successfully added.
   
   - **Response Body Example**:

```json
{
  "success": true,
  "message": "Nowy zespół Development Team został pomyślnie dodany.",
  "data": {
    "id": 1,
    "name": "Development Team"
  }
}
```

2. **400 Bad Request**
   
   - **Description**: The request body contains invalid or missing data. Validation errors will be returned in detail.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Nieprawidłowe dane wejściowe: Team name is required."
}
```

3. **500 Internal Server Error**
   
   - **Description**: An unexpected server error occurred while processing the request.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Wystąpił nieoczekiwany błąd podczas dodawania zespołu.",
  "error": "Error details (if available)"
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

This endpoint allows updating an existing team's name based on its unique identifier (`id`). If a team with the provided `id` does not exist, an appropriate error response is returned. The response contains structured messages about the success or failure of the update operation.

---

### Request

#### Method: `PATCH`

#### URL: `/teams/{id}`

#### Path Parameters

| Parameter | Type  | Required | Description                                      |
| --------- | ----- | -------- | ------------------------------------------------ |
| `id`      | `int` | Yes      | The unique identifier of the team to be updated. |

#### Headers

Headers are required for this request.

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

1. **200 OK**
   
   - **Description**: The team was successfully updated.
   
   - **Response Body Example**:

```json
{
  "success": true,
  "message": "Zespół Development Team został zaktualizowany na Updated Team Name.",
  "data": {
    "id": 1,
    "name": "Updated Team Name"
  }
}
```

2. **404 Not Found**
   
   - **Description**: The team with the specified ID does not exist.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Zespół o ID 1 nie został znaleziony."
}
```

3. **400 Bad Request**
   
   - **Description**: The provided data is invalid, or the update operation failed.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Nie udało się zaktualizować zespołu o ID 1."
}
```

4. **500 Internal Server Error**
- **Description**: An unexpected error occurred while 
  processing the request.

- **Response Body Example**:

```json
{
  "success": false,
  "message": "Wystąpił nieoczekiwany błąd podczas aktualizowania zespołu.",
  "error": "Error details (if available)"
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

This endpoint allows the deletion of an existing team from the system based on its unique identifier (`id`). If the team exists and is successfully deleted, a confirmation message is returned. If the team does not exist, or if there are issues with deletion, meaningful error responses are provided.

---

### Request

#### Method: `DELETE`

#### URL: `/teams/{id}`

#### Path Parameters

| Parameter | Type  | Required | Description                                      |
| --------- | ----- | -------- | ------------------------------------------------ |
| `id`      | `int` | Yes      | The unique identifier of the team to be deleted. |

#### Headers

Headers are optional for this request.

- **Content-Type**: `application/json`

---

### Response

#### Status Codes

1. **200 OK**
   
   - **Description**: The team was successfully deleted.
   
   - **Response Body Example**:

```json
{
  "success": true,
  "message": "Zespół Development Team został pomyślnie usunięty."
}
```

2. **404 Not Found**
   
   - **Description**: The team with the specified ID does not exist.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Zespół o ID 10 nie został znaleziony."
}
```

3. **400 Bad Request**
   
   - **Description**: The team exists, but the deletion operation could not be completed.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Nie udało się usunąć zespołu o ID 10."
}
```

4. **500 Internal Server Error**
   
   - **Description**: An unexpected server error occurred during the deletion process.
   
   - **Response Body Example**:

```json
{
  "success": false,
  "message": "Wystąpił nieoczekiwany błąd podczas usuwania zespołu.",
  "error": "Error details (if available)"
}
```

---

### CURL Example

```shell
curl -X DELETE http://localhost:8080/teams/1
```

---

## **Deployment**

To deploy the application:

1. Package it into a JAR file:

```shell
mvn package
```

The generated JAR file will be under the `target/` directory.

2. Run the JAR file:

```shell
java -jar target/team-management-api.jar
```

3. Ensure the application is connected to a production-ready database 
   with proper configurations.
