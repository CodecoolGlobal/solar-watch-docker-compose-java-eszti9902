# SolarWatch Project
SolarWatch is a full-stack web application that allows users to retrieve sunrise and sunset data for a specific city and date. The data is fetched from the database, but if the data is not available, the application retrieves it from an external API. The application also supports user roles, allowing administrators to modify the data stored in the database.

## Features
### User Registration and Login:
Users can register and log in.
![LogIn](./readme_images/sw_loginpage.png)
### Sunrise and Sunset Data Retrieval:
Users can search for sunrise and sunset data for a specific city and date.
![Data](./readme_images/sw_data_retrieval.png)
If the data is not available in the database, the application fetches it from an external API.

### Database Management (Administrators Only):
Administrators can modify the sunrise and sunset data stored in the database accessed through the "admin dashboard" button that is only visible for them.
![Admin MainPage](./readme_images/sw_admin_mainpage.png)
Admin dashboard:
![Admin dashboard](./readme_images/sw_admindashboard.png)

## Technology Stack

### Frontend:
- React.js
- CSS
- Nginx (for serving the React app in a Docker environment)

### Backend:
- Spring Boot (Java)
- Spring MVC
- Spring Data
- JPA
- Spring Security
- Hibernate
- RESTful API

### Database:
- PostgreSQL

### Containerization:
- Docker

### External APIs:
- https://sunrise-sunset.org/api
- https://openweathermap.org/api/geocoding-api

## Setup and Installation
### Local Setup
- Clone the repository: `git clone git@github.com:CodecoolGlobal/solar-watch-docker-compose-java-eszti9902.git`
- Navigate to the desired directory: `cd <directory>`
- Backend Setup:
  - Navigate to the solarwatch_backend directory: `cd solarwatch_backend`
  - Set up your PostgreSQL database.
  - Update application.properties with your database credentials and security data:
```bash
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password
POSTGRES_DB=your_db_name
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password
CODECOOL_APP_JWTSECRET=your_jwt_secret
CODECOOL_APP_JWTEXPIRATIONMS=your_jwt_expiration_time
```
*JWT secret key should be 64 characters long.*
  - Build and run the Spring Boot application: `mvn spring-boot:run`

- Frontend Setup:
  - Navigate to the solarwatch_frontend directory: `cd ../solarwatch_frontend`
  - Install dependencies using npm: `npm install`
  - Run the React development server: `npm start`

### Docker Setup
  - Build and run the application: `docker-compose up`
  - The frontend will be accessible at http://localhost:3000.
- Environment Variables
  - Create a .env file in the root directory with the following environment variables:
```bash
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password
POSTGRES_DB=your_db_name
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password
CODECOOL_APP_JWTSECRET=your_jwt_secret
CODECOOL_APP_JWTEXPIRATIONMS=your_jwt_expiration_time
```

*JWT secret key should be 64 characters long.*