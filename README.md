# EtsturTestCase Project

This project includes a React-based frontend application and a Spring Boot-based backend application.

## Requirements

- [Node.js](https://nodejs.org/) (for the Frontend)
- [npm](https://www.npmjs.com/) (for the Frontend)
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (for the Backend)
- [PostgreSQL](https://www.postgresql.org/) database

## Frontend (React) Setup

1. Navigate to the `frontend` folder: `cd frontend`.
   
2. Install the required dependencies by running the following command in the terminal:

```bash
npm install
```
   
## To start the application:

```bash
npm start
```

3. This command will run the frontend application locally.

---

## Backend (Spring Boot) Setup


1. Navigate to the `backend` folder: `cd backend`.

2. To set up the PostgreSQL database, configure the `application.properties` file located at `backend/src/main/resources/application.properties` with the necessary database connection details:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/db_name
spring.datasource.username=db_username
spring.datasource.password=db_password
```

## To start the application:
1. After configuring the database, build and install the required dependencies for the Spring project using Maven:

```bash
mvn clean install
```

2. Start the Spring Boot application by running the following command:

```bash
java -jar backend/target/application.jar
```

This command will launch the Spring Boot application.

## API Documentation

-> You can review the required technical requirements in the project from the following file: `Etstur-java-case.docx` located at the root path.

->  To access the Postman collection with predefined requests, import the file `EtsTurJavaTestCase.postman_collection.json` located at the root path.

->  To explore the backend API endpoints and make requests, you can access the Swagger documentation at `http://localhost:8080/swagger-ui/index.html`.

## Contact

->  For any questions or feedback regarding the project, you can contact us at safak.tamses@gmail.com .



