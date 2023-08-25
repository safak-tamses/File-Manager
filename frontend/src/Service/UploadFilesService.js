import axios from "axios";
import http from "../Common"
const API_URL = "http://localhost:8080/user/files";

class UploadFilesService {
  upload(file, onUploadProgress) {
    return http.post("/user/files", file, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      onUploadProgress,
    });
  }


  getFiles() {
    return http.get("user/files/all", {
    });
  }

  getFileDetails(fileId) {
    return http.get(`user/files/${fileId}/details`, {
    });
  }
}

export default new UploadFilesService();
