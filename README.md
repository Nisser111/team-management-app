# Team Management App

This project is a full-stack application for managing teams, built using Angular for the frontend and Spring Boot for the backend. It provides functionalities to manage teams and employees, supporting CRUD operations. This project was created as a recruitment project for internships. I'll be glad for any tips and help me improve my project.


## Tech Stack

![Angular](https://img.shields.io/badge/-Angular-DD0031?style=flat-square&logo=angular&logoColor=white)
![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/-MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![SCSS](https://img.shields.io/badge/-SCSS-CC6699?style=flat-square&logo=sass&logoColor=white)
![TypeScript](https://img.shields.io/badge/-TypeScript-007ACC?style=flat-square&logo=typescript&logoColor=white)
![HTML5](https://img.shields.io/badge/-HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)
![Java](https://img.shields.io/badge/-Java-007396?style=flat-square&logo=java&logoColor=white)

## Table of Contents

1. **Getting Started**
   - [Frontend](#frontend)
   - [Backend](#backend)
2. **Additional Resources**
3. **License**

---

## Getting Started

### Frontend

To set up and run the frontend application, follow these steps:

1. **Requirements**
   - Node.js (version 14 or higher)
   - Angular CLI (version 19.0.4)

2. **Clone the Repository**
   ```bash
    git clone https://github.com/Nisser111/team-menagement-app.git
    cd team-management-api/frontend
   ```

3. **Install Dependencies**
   ```bash
   npm install
   ```

4. **Run the Development Server**
   ```bash
   ng serve
   ```

5. **Access the Application**
   Open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

### Backend

To set up and run the backend application, follow these steps:
#### 1. Clone the repository

Clone the API repository to your local development environment:

```shell
git clone https://github.com/Nisser111/team-menagement-app.git
cd team-management-api/backend
```

#### 2. Configure the database

- Create a new database `menagement_system` and import neccessery data from [these script](/utils/database-run-script.sql).
  
  

- Update the database configurations in your `application.properties` 
  file located at `src/main/resources/`. Example:

```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/team_management
  spring.datasource.username=<your_username>
  spring.datasource.password=<your_password>
  spring.jpa.hibernate.ddl-auto=update
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

#### 3. Build the project

Use Maven to build the project:

```shell
mvn clean install
```

#### 4. Run the application

Start the application using the following command:

```shell
mvn spring-boot:run
```

The application will run on the default port **8080** unless otherwise specified in the `application.properties`.

> For full API documentation [look there](/backend/README.md). 

---

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.

For more information on using Spring Boot, refer to the [Spring Boot Documentation](https://spring.io/projects/spring-boot).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
