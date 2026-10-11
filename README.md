# uni-course-portal

University course management portal built with React, TypeScript, Material UI, React Admin, and two Kotlin/Spring Boot services.

## Features

- Admin pages for managing courses, students, secondary admins, and student status requests.
- Student pages for browsing courses, viewing enrolled courses, and viewing a profile.
- Responsive dashboard and light/dark themes.
- Course and student backend services in `services/`.

## Tech stack

- React 19 and TypeScript
- React Admin 5
- Material UI 7
- Vite
- Kotlin and Spring Boot
- MySQL, Kafka, and Redis for the backend services

## Run locally

Requirements: Node.js and npm for the frontend. The backend services require JDK 17 or newer, MySQL, Kafka, and Redis.

```sh
npm install
npm run dev
```

The frontend expects the backend services to be available locally:

- Student service: `http://localhost:8080/api/v1`
- Course service: `http://localhost:8081/api/v1`

## Backend services

The repository contains both backend applications:

- `services/student-service` runs on port `8080`.
- `services/course-service` runs on port `8081` (JobRunr dashboard on `8082`).

Both services use the local MySQL databases `student_db` and `courses_db`, plus Kafka on `localhost:9092`; the course service also uses Redis on `localhost:6379`. Configure these dependencies before starting the services.

Set the following environment variables in your shell or IDE run configurations. Keep real values out of Git:

```sh
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_long_random_secret
DEMO_STUDENT_PASSWORD=your_demo_student_password
DEMO_ADMIN_PASSWORD=your_demo_admin_password
```

The demo usernames default to `student-user` and `AdminDua`. You can override them with `DEMO_STUDENT_USERNAME` and `DEMO_ADMIN_USERNAME`.

From each service directory, start it with the Gradle wrapper:

```sh
./gradlew bootRun
```

Run the command once from `services/student-service` and once from `services/course-service` in separate terminals. On Windows, use `gradlew.bat bootRun`.

## Useful scripts

```sh
npm run type-check
npm run build
```
