# Investment App

Учебный **pet-проект** на Spring Boot + Kotlin + PostgreSQL + JWT + Docker, моделирующий мини-инвестиционную платформу.

---

## Функционал

- Регистрация и авторизация пользователей (JWT)
- Создание и пополнение счетов
- Просмотр истории транзакций
- Покупка и продажа акций
- Расчёт прибыли и убытков по портфелю
- Swagger UI для тестирования API

---

## Технологии

- Spring Boot 3
- Kotlin + Java
- Spring Security с JWT
- PostgreSQL + JPA/Hibernate
- Docker + Docker Compose
- Swagger (OpenAPI)

---

## Переменные окружения

Создайте файл `.env` в корне проекта на основе `.env.example` и заполните реальные значения:

# Database
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/investments_app
SPRING_DATASOURCE_USERNAME=your_db_username
SPRING_DATASOURCE_PASSWORD=your_db_password
```
# JWT
```
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000
```
# Spring
```
SPRING_PROFILES_ACTIVE=dev
```


## Запуск Проекта

### Через Docker
В корне проекта, где лежит docker-compose.yml, выполните:
```
cp .env.example .env          # создаём локальный .env
docker-compose up --build
```
•	Приложение будет доступно по адресу: http://localhost:8080
•	Swagger UI: http://localhost:8080/swagger-ui/index.html

Для остановки:
```
docker-compose down
```

###  Локально через IDE
1.	Настройте переменные окружения из .env или укажите их в application.properties.
2.	Запустите InvestmentsApplication.java как обычное Spring Boot приложение.
3.	Приложение стартует на http://localhost:8080.
---
## API
Swagger автоматически генерирует документацию для всех контроллеров:
http://localhost:8080/swagger-ui/index.html
---
## Структура проекта
investments/
│
├── src/main/kotlin        # Kotlin пакеты: Controller, DTO, Model, Repository, Security, Config
├── src/main/java          # Java пакеты: Service
├── src/main/resources/    # application.properties
├── Dockerfile
├── docker-compose.yml
├── .env.example
├── .gitignore
└── README.md
---
Автор
Артур — студент МИРЭА, pet-проекты на Spring Boot + Kotlin.



  
