import { fetchUtils } from "react-admin";
import simpleRestProvider from "ra-data-simple-rest";

// Add the JWT token to every request
const httpClient = async (url: string, options: any = {}) => {
  const token = localStorage.getItem("token");

  if (!options.headers) {
    options.headers = new Headers({ Accept: "application/json" });
  }

  if (token) {
    options.headers.set("Authorization", `Bearer ${token}`);
  }

  //send the request
  return fetchUtils.fetchJson(url, options);
};


// Connect React Admin 
//course
const courseProvider = simpleRestProvider(
  "http://localhost:8081/api/v1",
  httpClient
);
//student
const studentProvider = simpleRestProvider(
  "http://localhost:8080/api/v1",
  httpClient
);


//get the number of enrolled students for each course
const getEnrollmentCounts = async () => {
  const response = await httpClient(
    "http://localhost:8080/api/v1/enrollment-counts"
  );

  return response.json as Record<string, number>;
};


//create the main data provider
export const dataProvider = {
  ...courseProvider,


  //list of records
  getList: async (resource: string, params: any) => {

    // get students 
    if (resource === "students") {
      return studentProvider.getList("students", params);
    }


    // get secondary admins 
    if (resource === "admins") {
      const response = await httpClient(
        "http://localhost:8080/api/v1/users/secondary-admins"
      );

      return {
        data: response.json,
        total: response.json.length,
      };
    }


    // get status requests 
    if (resource === "statusRequests") {
      const response = await httpClient(
        "http://localhost:8080/api/v1/admin/status-requests"
      );

      return {
        data: response.json,
        total: response.json.length,
      };
    }


    // student's profile
    if (resource === "myProfile") {
      const response = await httpClient(
        "http://localhost:8080/api/v1/me/profile"
      );

      return {
        data: [response.json],
        total: 1,
      };
    }


    // student's courses
    if (resource === "myCourses") {
      const response = await httpClient(
        "http://localhost:8080/api/v1/me/courses"
      );

      return {
        data: response.json,
        total: response.json.length,
      };
    }


    // Get students enrolled in a specific course
    if (resource === "courseStudents") {
      const courseId = params.filter.courseId;

      const response = await httpClient(
        `http://localhost:8080/api/v1/courses/${courseId}/students`
      );

      return {
        data: response.json,
        total: response.json.length,
      };
    }


    // get courses from the Course Service
    const result = await courseProvider.getList(resource, params);


    // the number of enrolled students to each course
    if (resource === "courses") {
      const enrollmentCounts = await getEnrollmentCounts();

      return {
        ...result,

        data: result.data.map((course) => ({
          ...course,
          enrolledCount: enrollmentCounts[String(course.id)] ?? 0,
        })),
      };
    }

    return result;
  },


  // get one record by id
  getOne: (resource: string, params: any) => {

    if (resource === "students") {
      return studentProvider.getOne("students", params);
    }
    if (resource === "courses") {
      return Promise.all([
        courseProvider.getOne(resource, params),
        getEnrollmentCounts(),
      ]).then(([result, counts]) => ({
        ...result,

        data: {
          ...result.data,
          enrolledCount: counts[String(result.data.id)] ?? 0,
        },
      }));
    }
    return courseProvider.getOne(resource, params);
  },

  // Create a new record
  create: (resource: string, params: any) => {

    if (resource === "students" || resource === "users") {
      return studentProvider.create(resource, params);
    }
    return courseProvider.create(resource, params);
  },


  // update a record
  update: (resource: string, params: any) => {

    if (resource === "students") {
      return studentProvider.update("students", params);
    }

    return courseProvider.update(resource, params);
  },


  //delete a record
  delete: (resource: string, params: any) => {

    if (resource === "students") {
      return studentProvider.delete("students", params);
    }
    return courseProvider.delete(resource, params);
  },


  // get multiple records by ids
  getMany: (resource: string, params: any) => {

    if (resource === "students") {
      return studentProvider.getMany("students", params);
    }
    return courseProvider.getMany(resource, params);
  },


  // get records related to another resource
  getManyReference: (resource: string, params: any) => {

    if (resource === "students") {
      return studentProvider.getManyReference("students", params);
    }
    return courseProvider.getManyReference(resource, params);
  },


  // update multiple records
  updateMany: (resource: string, params: any) => {

    if (resource === "students") {
      return studentProvider.updateMany("students", params);
    }
    return courseProvider.updateMany(resource, params);
  },


  // delete multiple records
  deleteMany: (resource: string, params: any) => {

    if (resource === "students") {
      return studentProvider.deleteMany("students", params);
    }
    return courseProvider.deleteMany(resource, params);
  },
};