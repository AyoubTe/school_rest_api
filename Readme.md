# 🎓 School Management REST API


A
 robust, secure, and fully-featured RESTful API built with Spring Boot for managing a complete school ecosystem. It handles users (Students, Teachers, Admins), academic entities (Schools, Courses, Assignments), and grading systems, all protected by Role-Based Access Control (RBAC) using JSON Web Tokens (JWT).
---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.4.x |
| Security | Spring Security + JWT (JJWT 0.12.x) |
| Database | PostgreSQL |
| Migrations | Flyway |
| ORM | Spring Data JPA / Hibernate |
| Build tool | Gradle |
| Mapping | MapStruct |
| Documentation | SpringDoc OpenAPI (Swagger UI) |
| Dev tools | Spring Boot DevTools, Docker Compose |

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- PostgreSQL running locally (or via Docker)
- Gradle 8+

### 1. Clone the repository

```bash
git clone https://github.com/your-username/school_manage_rest_api.git
cd school_manage_rest_api
```

### 2. Configure the database

Create a PostgreSQL database:

```sql
CREATE DATABASE school_db;
CREATE USER school_user WITH PASSWORD 'yourpassword';
GRANT ALL PRIVILEGES ON DATABASE school_db TO school_user;
```

### 3. Configure `application.yml`

```yaml
spring:
  application:
    name: school_manage_rest_api
  datasource:
    url: jdbc:postgresql://localhost:5432/school_db
    username: school_user
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: validate
  flyway:
    enabled: true
    locations: classpath:db/migration

application:
  security:
    jwt:
      secret-key: your-256-bit-base64-encoded-secret
      expiration: 7200000   # 2 hours in milliseconds
```

> **Tip:** Generate a secure secret key with:
> ```bash
> openssl rand -base64 64
> ```

### 4. Run the application

```bash
./gradlew bootRun
```

Flyway will automatically run all migrations and seed the database on first startup.

---

## 📖 API Documentation

Once the app is running, open Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## 🔐 Authentication

The API uses **JWT Bearer token** authentication.

### Register

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "etu.test",
    "firstname": "Test",
    "lastname": "User",
    "email": "test@school.net",
    "password": "password123",
    "role": "STUDENT"
  }'
```

Accepted roles: `STUDENT`, `TEACHER`, `ADMIN`

> For `TEACHER` role, also provide `"discipline"` and `"schoolId"`.

### Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H 'Content-Type: application/json' \
  -d '{
    "emailOrUsername": "test@school.net",
    "password": "password123"
  }'
```

Response:

```json
{
  "token": "eyJhbGci...",
  "message": "Login successful",
  "role": "STUDENT"
}
```

Use the token in subsequent requests:

```bash
curl -H 'Authorization: Bearer eyJhbGci...' \
  http://localhost:8080/api/v1/students
```

### Logout

JWT is stateless — logout is handled client-side by discarding the token.

```bash
curl -X POST http://localhost:8080/api/v1/auth/logout
```

---

## 📡 API Endpoints

### 🔓 Public

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Register a new user |
| POST | `/api/v1/auth/login` | Login and get JWT token |
| POST | `/api/v1/auth/logout` | Logout (client-side) |

### 👨‍🎓 Students — `STUDENT`, `ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/students` | Get all students |
| GET | `/api/v1/students/{id}` | Get student by ID |
| POST | `/api/v1/students` | Create a student |
| PUT | `/api/v1/students` | Update a student |
| DELETE | `/api/v1/students/{id}` | Delete a student |
| GET | `/api/v1/students/{id}/courses` | Get student's courses |
| GET | `/api/v1/students/{id}/grades` | Get student's grades |

### 👨‍🏫 Teachers — `TEACHER`, `ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/teachers` | Get all teachers |
| GET | `/api/v1/teachers/{id}` | Get teacher by ID |
| POST | `/api/v1/teachers` | Create a teacher |
| PUT | `/api/v1/teachers` | Update a teacher |
| DELETE | `/api/v1/teachers/{id}` | Delete a teacher |
| GET | `/api/v1/teachers/{id}/courses` | Get teacher's courses |
| GET | `/api/v1/teachers/{id}/school` | Get teacher's school |

### 🛡️ Admins — `ADMIN` only

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/admins` | Get all admins |
| GET | `/api/v1/admins/{id}` | Get admin by ID |
| POST | `/api/v1/admins` | Create an admin |
| PUT | `/api/v1/admins` | Update an admin |
| DELETE | `/api/v1/admins/{id}` | Delete an admin |

### 📚 Courses — Authenticated

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/courses` | Get all courses |
| GET | `/api/v1/courses/{id}` | Get course by ID |
| POST | `/api/v1/courses` | Create a course |
| PUT | `/api/v1/courses` | Update a course |
| DELETE | `/api/v1/courses/{id}` | Delete a course |
| GET | `/api/v1/courses/{id}/teacher` | Get course's teacher |
| GET | `/api/v1/courses/{id}/students` | Get course's students |

### 📝 Assignments — Authenticated

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/assignments` | Get all assignments |
| GET | `/api/v1/assignments/{id}` | Get assignment by ID |
| POST | `/api/v1/assignments` | Create an assignment |
| PUT | `/api/v1/assignments` | Update an assignment |
| DELETE | `/api/v1/assignments/{id}` | Delete an assignment |

### 📊 Grades — Authenticated

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/gradeDetails` | Get all grade records |
| GET | `/api/v1/gradeDetails/{id}` | Get grade by ID |
| POST | `/api/v1/gradeDetails` | Create a grade record |
| PUT | `/api/v1/gradeDetails` | Update a grade record |
| DELETE | `/api/v1/gradeDetails/{id}` | Delete a grade record |

### 🏫 Schools — Authenticated

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/schools` | Get all schools |
| GET | `/api/v1/schools/{id}` | Get school by ID |
| POST | `/api/v1/schools` | Create a school |
| PUT | `/api/v1/schools` | Update a school |
| DELETE | `/api/v1/schools/{id}` | Delete a school |

---

## 🗄️ Database Schema

```
role ──────────────┐
                   ▼
admins         (id, username, email, password, role, mission)
students       (id, username, email, password, role)
teachers       (id, username, email, password, role, discipline, school_id)
schools        (id, school_name, school_address)
courses        (id, course_code, course_name, teacher_id)
assignments    (id, assignment_name, description, due_date)
grade_details  (id, first_grade, second_grade, third_grade)

student_course_details  (id, student_id, course_id, grade_details_id)
assignment_details      (id, assignment_id, student_id, is_done)
```

---

## 🌱 Seed Data

The Flyway migrations include mock data for testing:

| Type | Count | Password |
|------|-------|---------|
| Admins | 10 | `password123` |
| Teachers | 6 | `password123` |
| Students | 28 | `password123` |
| Courses | 6 | — |
| Schools | 2 | — |

Sample accounts to get started:

| Role | Username / Email | Password |
|------|-----------------|----------|
| ADMIN | `pierre.dupont@school.net` | `password123` |
| TEACHER | `prof.lovelace` | `password123` |
| STUDENT | `etu.simon` | `password123` |

---

## 📁 Project Structure

```
src/main/java/net/samiayoub/school/
├── config/
├── controller/
│   ├── AuthController.java
│   ├── StudentController.java
│   ├── TeacherController.java
│   ├── AdminController.java
│   ├── CourseController.java
│   ├── AssignmentController.java
│   └── ...
├── dto/
│   ├── requests/
│   └── responses/
├── entity/
│   ├── User.java
│   ├── Student.java
│   ├── Teacher.java
│   ├── Admin.java
│   └── ...
├── exception/
├── mapper/
├── repository/
├── security/
│   └── jwt/
│       ├── JwtTokenProvider.java
│       └── JwtAuthenticationFilter.java
└── service/
    ├── AuthService.java
    ├── CustomUserDetailsService.java
    └── ...

src/main/resources/
├── db/migration/
│   ├── V1__create_schema.sql
│   ├── V2__seed_data.sql
│   └── ...
└── application.yml
```

---

## 📄 License

MIT