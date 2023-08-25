import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import "../Style/Page.css";
import "bootstrap/dist/css/bootstrap.min.css";
import UploadFiles from "./UploadFilesComponent";
import sendAuthenticatedGetRequest from "../Service/sendAuthenticatedGetRequest";

function Page() {
  const [isRequestSuccess, setIsRequestSuccess] = useState(null);

  useEffect(() => {
    sendAuthenticatedGetRequest()
      .then((isSuccess) => {
        setIsRequestSuccess(isSuccess);
      })
      .catch((error) => {
        setIsRequestSuccess(false);
        console.error("Error during API request:", error); // Hata konsola yazdırılıyor
      });
  }, []);

  if (isRequestSuccess === false) {
    return <Navigate to="/login" />;
  }

  return (
    <div>
      <nav className="navbar navbar-dark bg-dark">
        <div className="btn-group mx-auto">
          <h2 className="text-white">
            Spring Boot + React Drag and Drop File Upload & Download
          </h2>
        </div>
      </nav>
      <br></br>
      <div className="container">
        <UploadFiles />
      </div>
    </div>
  );
}

export default Page;
