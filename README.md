# SolarWatch Project
SolarWatch is a full-stack web application that allows users to retrieve sunrise and sunset data for a specific city and date. The data is fetched from the database, but if the data is not available, the application retrieves it from an external API. The application also supports user roles, allowing administrators to modify the data stored in the database.

## Features
### User Registration and Login:
- Users can register and log in.
- Administrators have special permissions.

### Sunrise and Sunset Data Retrieval:
- Users can search for sunrise and sunset data for a specific city and date.
- If the data is not available in the database, the application fetches it from an external API.

### Database Management (Administrators Only):
- Administrators can modify the sunrise and sunset data stored in the database.

## Technology Stack

### Frontend:
- React.js
- CSS
- Nginx (for serving the React app in a Docker environment)

### Backend:
- Spring Boot (Java)
- RESTful API
- PostgreSQL database

### Database:
- PostgreSQL

### External APIs:
- https://sunrise-sunset.org/api
- https://openweathermap.org/api/geocoding-api

### Docker:
- The entire application runs in Docker containers, including the frontend, backend, and database services.

## Setup and Installation
### - Local Setup
- Clone the repository: `git clone https://github.com/CodecoolGlobal/solar-watch-docker-compose-java-your_username`
- Navigate to the project directory: `cd solarwatch`
- Backend Setup:
  - Navigate to the solarwatch_backend directory.
  - Set up your PostgreSQL database.
  - Update application.properties with your database credentials.
  - Build and run the Spring Boot application.

- Frontend Setup:
  - Navigate to the solarwatch_frontend directory.
  - Install dependencies using npm: `npm install`
  - Run the React development server: `npm start`

### - Docker Setup
  - Build and run the application: `docker-compose up --build`
  - The frontend will be accessible at http://localhost:3000, and the backend API will be available at http://localhost:8080/api.
- Environment Variables
  - Create a .env file in the root directory with the following environment variables:
`
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password
POSTGRES_DB=your_db_name
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password
CODECOOL_APP_JWTSECRET=your_jwt_secret
CODECOOL_APP_JWTEXPIRATIONMS=your_jwt_expiration_time`