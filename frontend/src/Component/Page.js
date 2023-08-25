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
  const quit = () =>{
    localStorage.removeItem('token')
    window.location.href = "/login";
  }

  return (
    <div>
      <nav className="navbar navbar-dark bg-dark">
        <div className="btn-group mx-auto">
          <h3 className="text-white">
            Etstur Test Case Spring Boot + React Drag and Drop File Upload & Download
          </h3>
          <button className="text-white" onClick={() => quit()}>Logout</button>
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
