import { Formik, Form, Field, ErrorMessage } from "formik";
import React from "react";
import * as Yup from "yup";
import { useAuth } from "../contexts/AuthContext";
import { Link } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import GoogleButton from "react-google-button";
import { GoogleAuthProvider } from "firebase/auth";
export default function Login() {
  const navigate = useNavigate();
  const initialValues = {
    email: "",
    password: "",
  };
  const { login, logInWithGoogle } = useAuth();
  const validationSchema = Yup.object({
    email: Yup.string().email().required("Required"),
    password: Yup.string()
      .min(8, "Must have a minimum of 8 characters")
      .required("Required"),
  });

  async function handleSubmit(email, password) {
    try {
      await login(email, password);
      navigate("/dashboard");
    } catch (err) {
      toast.error(`LogIn failed: ${err.message}`, {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
      });
    }
  }

  function handleGoogleAuth() {
    logInWithGoogle();
  }

  return (
    <div className="min-h-screen flex flex-col gap-10 justify-center items-center bg-gray-100">
      <h1 className="text-6xl">Log In</h1>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={(values) => {
          handleSubmit(values.email, values.password);
        }}
      >
        <Form className="flex flex-col  text-2xl border  rounded-md shadow-md p-12 gap-3 bg-white">
          <label htmlFor="email">Email</label>
          <Field name="email" type="text" className="border  rounded-md p-3" />
          <div className="text-red-600">
            <ErrorMessage name="email" className="text-red-800" />
          </div>

          <label htmlFor="password">Password</label>
          <Field
            name="password"
            type="password"
            className="border  rounded-md p-3"
          />
          <div className="text-red-600">
            <ErrorMessage name="password" />
          </div>

          <button
            type="submit"
            className="text-white bg-blue-700 p-4 rounded-md hover:bg-blue-600"
          >
            Submit
          </button>
          <ToastContainer />
        </Form>
      </Formik>
      <GoogleButton onClick={handleGoogleAuth} />
      <div className="flex flex-col gap-10">
        <div>
          Already have an account? <Link to="/">Sign up</Link>
        </div>
        <div>
          Forgot password? <Link to="/resetPassword">Reset Password</Link>
        </div>
      </div>
    </div>
  );
}
