import React, { useEffect } from "react";
import "../Style/Page.css";
import "bootstrap/dist/css/bootstrap.min.css";
import UploadFiles from "./UploadFilesComponent";
import sendAuthenticatedGetRequest from "../Service/sendAuthenticatedGetRequest";

function Page() {
  useEffect(() => {
    sendAuthenticatedGetRequest()
      .then((isSuccess) => {
        console.log(isSuccess);
        if (isSuccess === false) {
          redirect("/login");
        }
      })
      .catch((error) => {
        console.error("Error during API request:", error);
      });
  }, []);
  function redirect(url) {
    window.location.href = url;
  }

  const quit = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  return (
    <div className="flex flex-col">
      <nav className="w-full h-20 flex justify-center items-center bg-gray-300 relative">
      <button className="absolute right-4 flex justify-center items-center ring-offset-2 ring" onClick={() => quit()}>Logout</button>
        <div className="w-6/8">
          <h3 className="text-black text-center">
           File Manager & Drag and Drop File Storage
          </h3>
        </div>
      </nav>
      <br />
      <div className="container">
        <UploadFiles />
      </div>
    </div>
  );
}

export default Page;
