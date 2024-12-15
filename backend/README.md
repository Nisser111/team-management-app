

# Getting Started with the Team Management API

This is the guide to help you quickly set up, run, and interact with the Team anagement API, which provides functionalities to manage teams in the system. The API supports CRUD operations, enabling clients to retrieve, create, update, and delete team entities.

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

- Create a new database `menagement_system`. Use database run script:
  
  [Database-run-script](https://github.com/Nisser111/team-menagement-app/blob/main/utils/database-run-script.sql)

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

1. **200 OK**
   
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

2. **500 Internal Server Error**
   
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

1. **200 OK**
   
   - **Description**: The employee was successfully created.
   
   - **Response Body Example**:

```json
 {
   "message": "New employee has been added successfully."
 }
```

2. **400 Bad Request**
   
   - **Description**: The request contains invalid data, such as a duplicate email, nvalid fields, or missing required fields.
   
   - **Response Body Example**:

```json
 {
   "error": "Email is already in use."
 }
```

3. **500 Internal Server Error**
   
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

1. **200 OK**
   
   - **Description**: The employee was successfully updated.
   
   - **Response Body Example**:

```json
{
  "message": "Employee updated successfully."
}
```

2. **400 Bad Request**
   
   - **Description**: The request contains invalid data, such as a duplicate email, invalid fields, or missing required fields.
   
   - **Response Body Example**:

```json
{
  "error": "Email is already in use."
}
```

3. **404 Not Found**
   
   - **Description**: No employee exists with the specified ID.
   
   - **Response Body Example**:

```json
 {
   "error": "Employee not found."
 }
```

4. **500 Internal Server Error**
   
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

1. **200 OK**
   
   - **Description**: The employee was successfully 
     deleted.
   
   - **Response Body Example**:

```json
{
  "message": "Employee with ID 1 has been deleted."
}
```

2. **404 Not Found**
   
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

1. **200 OK**
   
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

2. 500 Internal Server Error
   
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

1. **200 OK**
   
   - **Description**: The team was successfully added.
   
   - **Response Body Example**:

```json
{
  "message": "New team has been added successfully."
}
```

2. **400 Bad Request**
   
   - **Description**: The request body contains invalid or missing data. Validation errors will be returned in detail.
   
   - **Response Body Example**:

```json
{
  "error": "Team name is required"
}
```

3. **500 Internal Server Error**
   
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

Here is the REST API documentation for the `PATCH /teams/{id}` 
route.

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

1. **200 OK**
   
   - **Description**: The team was successfully updated.
   
   - **Response Body Example**:

```json
{
  "message": "Development Team has been updated to New Team Name."
}
```

2. **404 Not Found**
   
   - **Description**: The team with the specified ID does 
     not exist.
   
   - **Response Body Example**:

```json
{
  "error": "Team with ID 1 not found."
}
```

3. **400 Bad Request**
   
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

1. **200 OK**
   
   - **Description**: The team was successfully deleted.
   
   - **Response Body Example**:

```json
{
  "message": "Development Team has been deleted."
}
```

2. **404 Not Found**
   
   - **Description**: The team with the specified ID does 
     not exist.
   
   - **Response Body Example**:

```json
{
  "error": "Team with ID 10 not found."
}
```

3. **500 Internal Server Error**
   
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
