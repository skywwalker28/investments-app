# Investment App

A mini investment platform built with Spring Boot, Kotlin, PostgreSQL, JWT, and Docker.
This pet project simulates basic investment operations and portfolio management.

---

##  Features
- User registration and login with JWT authentication
- Create and manage accounts
- Deposit money into accounts
- Buy and sell stocks
- View transaction history
- Track portfolio profit and loss
- Swagger UI for interactive API documentation


---

## Technologies
- Spring Boot 3
- Kotlin & Java (backend services)
- Spring Security with JWT
- PostgreSQL + JPA/Hibernate
- Docker & Docker Compose
- Swagger (OpenAPI)

---

## Environment Variables

Create a `.env` file in the project root based on `.env.example`. Fill in your credentials and JWT secret:

### Database
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/investments_app
SPRING_DATASOURCE_USERNAME=your_db_username
SPRING_DATASOURCE_PASSWORD=your_db_password
```
### JWT
```
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000
```
### Spring
```
SPRING_PROFILES_ACTIVE=dev
```


## Running the Project
### Using Docker
1.	Copy .env.example to .env and fill in real values.
2.	Run the following command in the project root (where docker-compose.yml is):
```
docker-compose up --build
```
3. The app will be available at: http://localhost:8080
4. Swagger UI: http://localhost:8080/swagger-ui/index.html

To stop the app:

```
docker-compose down
```

### Running Locally
1.	Configure environment variables or application.properties.
2.	Run InvestmentsApplication.java from your IDE as a Spring Boot application.
3.	The app starts on http://localhost:8080.
---

Author: Artur (Telegram: @wskywalk)




  
