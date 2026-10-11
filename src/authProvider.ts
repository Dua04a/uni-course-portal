import { AuthProvider } from "react-admin";

//get the users role from the JWT token
const getRole = (token: string): string => {
  const payload = token.split(".")[1].replace(/-/g, "+").replace(/_/g, "/");
  return JSON.parse(atob(payload)).role;
};

export const authProvider: AuthProvider = {
  
  //login the user then save the token& role
  login: async ({ username, password }) => {
    let endpoint = "http://localhost:8080/api/login";
    let role = "";

//admin login endpoint
    if (username === "AdminDua") {
      endpoint = "http://localhost:8080/api/admin-token";
      role = "ADMIN";
    }
    
///student login endpoint
    else if (username === "student-user") {
      endpoint = "http://localhost:8080/api/token";
      role = "STUDENT";
    }

    try {
      //send information to the backend
      const response = await fetch(endpoint, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      //reject 
      if (!response.ok)
        return Promise.reject(new Error("Invalid username or password"));

      //get the JWT token from the backend
      const token = await response.text();

      //get the role from the token if it was not already set
      if (!role) role = getRole(token);

      //save the login information in localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("username", username);
      localStorage.setItem("role", role);

      //tell React admin that the login was successful
      return Promise.resolve();

      // login errors
    } catch (error) {
      console.error("Login failed:", error);
      return Promise.reject(new Error("Login failed"));
    }
  },

  //remove the user's login information when logging out
  logout: () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
    return Promise.resolve();
  },

  //Check if the user is authenticated
  checkAuth: () =>
    localStorage.getItem("token")
      ? Promise.resolve()
      : Promise.reject(new Error("Not authenticated")),

  // handle authentication errors from the backend
  checkError: (error) => {
    
    //log out the user if the token is unauthorized or expired
    if (error.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("username");
      localStorage.removeItem("role");
      return Promise.reject();
    }

    // Ignore other errors
    return Promise.resolve();
  },

  // Return the users role to React Admin
  getPermissions: () => Promise.resolve(localStorage.getItem("role")),
};