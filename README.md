# Uni Course Portal

A university course portal I built to make it easier for admins and students to manage courses in one place. This project helped me learn a lot of new things, especially how a React app talks to Spring Boot services and how to build pages for different user roles.

## What you can do

- Admins can manage courses, students, secondary admins, and student status requests.
- Students can browse courses, enroll in courses, drop a course, and view their enrolled courses and profile.
- Students can request to defer a term, withdraw from the university, or graduate. Admins can review and approve or reject those requests.
- The app has light and dark themes.

## Screenshots

I’m adding screenshots for these pages:

- Admin homepage
- Secondary admins
- Create account by admin
- Student homepage
- Enrolled courses
- Student profile

## Built with

- React, TypeScript, React Admin, and Material UI
- Kotlin and Spring Boot
- MySQL, Kafka, and Redis

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

You’ll need JDK 17 or newer, MySQL, Kafka, and Redis running locally. The services use the MySQL databases `student_db` and `courses_db`, Kafka at `localhost:9092`, and Redis at `localhost:6379`.

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
