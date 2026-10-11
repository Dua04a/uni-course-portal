# Uni Course Portal

A university course portal I built to make it easier for admins and students to manage courses in one place. I learned a lot while building it, especially about React, Kotlin, and connecting separate backend services.

## What you can do

- Admins can manage courses, students, secondary admins, and student status requests.
- Students can browse courses, enroll in courses, drop a course, and view their enrolled courses and profile.
- Students can request to defer a term, withdraw from the university, or graduate. Admins can review and approve or reject those requests.
- The app has light and dark themes.

## What I learned

I built the backend as two microservices: one for students and one for courses. They talk to each other through REST, and use Kafka events to keep course enrollment counts in sync. I also got to work with JWT login, MySQL and Spring Data JPA, Redis caching, and background jobs with JobRunr. Elide is set up for JSON:API and GraphQL endpoints too.

## Screenshots

I’m adding screenshots for these pages:

Admin homepage

<img width="2880" height="1626" alt="Pasted Graphic" src="https://github.com/user-attachments/assets/13b9c441-837e-47ef-807c-8226d6b420aa" />

Secondary admins

<img width="2876" height="1624" alt="Pasted Graphic 1" src="https://github.com/user-attachments/assets/1f5f8578-f11b-4d7d-9e48-3e78b613f10f" />

Create account by admin

<img width="2880" height="1620" alt="Pasted Graphic 2" src="https://github.com/user-attachments/assets/5d346aeb-f5ef-48e6-aee9-19c26e44e0b2" />

Student homepage

<img width="2880" height="1622" alt="Pasted Graphic 3" src="https://github.com/user-attachments/assets/ccaa43e3-881c-4157-a189-87df2badc8e8" />

Enrolled courses

<img width="2880" height="1622" alt="Pasted Graphic 4" src="https://github.com/user-attachments/assets/6a6b8d88-6ddf-41bc-9b3a-cf37c7f3a62c" />

Student profile

<img width="2880" height="1622" alt="Pasted Graphic 5" src="https://github.com/user-attachments/assets/44207b0f-61ca-408a-833b-fe50846d77b7" />

## Built with

- React, TypeScript, React Admin, and Material UI
- Kotlin and Spring Boot
- Apache Kafka for enrollment events between services
- JWT for authentication
- Spring Data JPA and MySQL for storing data
- Redis for caching courses
- JobRunr for background jobs
- Elide for JSON:API and GraphQL endpoints

## Run the frontend

You’ll need Node.js and npm.

```sh
npm install
npm run dev
```

The frontend uses the backend services at `http://localhost:8080` and `http://localhost:8081`.

## Run the backend

The two services are in the `services` folder:

- `student-service` runs on port `8080`.
- `course-service` runs on port `8081` (JobRunr dashboard: `8082`).

You’ll need JDK 21, MySQL, Kafka, and Redis running locally. The services use the MySQL databases `student_db` and `courses_db`, Kafka at `localhost:9092`, and Redis at `localhost:6379`.

Add these environment variables to your IDE run settings or terminal before starting the services:

```sh
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_long_random_secret
DEMO_STUDENT_PASSWORD=your_demo_student_password
DEMO_ADMIN_PASSWORD=your_demo_admin_password
```

The demo usernames default to `student-user` and `AdminDua`. You can change them with `DEMO_STUDENT_USERNAME` and `DEMO_ADMIN_USERNAME`. Keep your actual passwords and secrets private; don’t commit them to GitHub.

Open a terminal in each service folder and run:

```sh
./gradlew bootRun
```

On Windows, use `gradlew.bat bootRun`.

## A couple useful commands

```sh
npm run type-check
npm run build
```
