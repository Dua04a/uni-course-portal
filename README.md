# University Course Portal

A role-based university course management interface built with React Admin, React, TypeScript, and Material UI.

## Features

- Admin pages for managing courses, students, secondary admins, and student status requests.
- Student pages for browsing courses, viewing enrolled courses, and viewing a profile.
- Responsive dashboard and light/dark themes.

## Tech stack

- React 19 and TypeScript
- React Admin 5
- Material UI 7
- Vite

## Run locally

Requirements: Node.js and npm.

```sh
npm install
npm run dev
```

The frontend expects the backend services to be available locally:

- Student service: `http://localhost:8080/api/v1`
- Course service: `http://localhost:8081/api/v1`

The backend service source code is not included in this repository.

## Useful scripts

```sh
npm run type-check
npm run build
```
