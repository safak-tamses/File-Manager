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

  async downloadFile(fileId, fileName) {
    try {
      const token = localStorage.getItem("token");

      if (!token) {
        throw new Error("No token found");
      }

      const url = `http://localhost:8080/user/files/${fileId}`;
      const response = await axios.get(url, {
        responseType: "arraybuffer", // Byte array olarak almak için
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const blob = new Blob([response.data], { type: "application/octet-stream" });
      const link = document.createElement("a");
      link.href = window.URL.createObjectURL(blob);
      link.download = fileName; // İstenilen dosya adını burada kullanıyoruz
      link.click();

      return response.data; // Byte array olarak döndürüyoruz
    } catch (error) {
      console.error("Error downloading file:", error);
      throw new Error("File download failed");
    }
  }
}

export default new UploadFilesService();
