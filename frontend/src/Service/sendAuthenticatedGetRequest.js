import axios from "axios";

const sendAuthenticatedGetRequest = async () => {
  try {
    if (
      localStorage.getItem("token") === null ||
      localStorage.getItem("token") === ""
    ) {
      return false;
    } else {
      const url = "http://localhost:8080/loginWithToken";
      const token = localStorage.getItem("token");

      if (!token) {
        throw new Error("No token found");
      }

      const config = {
        method: "get",
        url,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };

      const response = await axios(config);

      return response.data.isSuccess;
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response && error.response.status === 403) {
        // Handle 403 status code here
        // You can redirect the user to a login page or show an error message
        // For now, I'm just returning 'false' to indicate failure
        return false;
      }
      console.error("API Request Error:", error);
      throw new Error("API request failed");
    }
  }
};

export default sendAuthenticatedGetRequest;
