import React, { useEffect, useState } from "react";
import axios from "axios";
import { API_URL } from "../config";
import sendAuthenticatedGetRequest from "../Service/sendAuthenticatedGetRequest";
function LoginRegister() {
  const [message,setMessage] = useState(null);

  async function loginCheck() {
    try {
      const isRequestSuccess = await sendAuthenticatedGetRequest();
      return isRequestSuccess;
    } catch (error) {
      return false;
    }
  }

  function redirect(url) {
    window.location.href = url;
  }

  function loginAlready(isRequestSuccess) {
    if (isRequestSuccess) {
      redirect("/index");
    }
  }

  async function checkAndRedirect() {
    const isRequestSuccess = await loginCheck();
    loginAlready(isRequestSuccess);
  }

  useEffect(() => {
    checkAndRedirect();
  }, []);

  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const endpoint = isLogin ? "login" : "register";

    const requestData = {
      username: formData.username,
      password: formData.password,
    };

    try {
      const response = await axios.post(`${API_URL}${endpoint}`, requestData);
      console.log(endpoint)
      if(endpoint === 'login'){
        if(response.data.errorMessage === 'Login failed.'){
          setMessage(response.data.errorMessage)
        }else{
          console.log(response.data)
          localStorage.removeItem('token')
          localStorage.setItem('token',response.data.data.token)
          redirect("/index")
        }

      }
      if(endpoint === 'register'){
        console.log(response.data)
        if(response.data.errorMessage === 'Username already exists'){
          setMessage(response.data.errorMessage)
        }
        if(response.data.data.message === 'User registration successfully.'){
          setMessage(response.data.data.message)
        }
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-teal-300 via-indigo-300 to-fuchsia-200">
      <div className="bg-white p-8 rounded shadow-md w-96 bg-gradient-to-tr from-stone-200 via-stone-300 to-slate-200">
        <div className="text-center mb-6">
          <h2 className="text-2xl text-red-600">
            {isLogin ? "Login" : "Register"}
          </h2>
        </div>
        <form
          onSubmit={handleSubmit}
          className={`${isLogin ? "login" : "register"}-form `}
        >
          <div className="mb-4">
            <label htmlFor="username" className="block mb-1 text-gray-600">
              Username
            </label>
            <input
              type="text"
              id="username"
              name="username"
              value={formData.username}
              onChange={handleFormChange}
              className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-red-400 "
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block mb-1 text-gray-600">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleFormChange}
              className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-red-400 mb-2"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-red-500 text-white py-2 rounded hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-400"
          >
            {isLogin ? "Login" : "Register"}
          </button>
          <div className="w-full flex justify-center items-center mt-4">{message}</div>
        </form>
        <div className="mt-2 text-center text-gray-600">
          {isLogin ? "Don't have an account?" : "Already have an account?"}
          <br />
          <button
            className="mt-2 text-red-500 hover:underline focus:outline-none"
            onClick={() => setIsLogin(!isLogin)}
          >
            {isLogin ? "Register instead" : "Login instead"}
          </button>
        </div>
      </div>
    </div>
  );
}

export default LoginRegister;
