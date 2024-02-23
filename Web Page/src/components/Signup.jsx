import { Formik, Form, Field, ErrorMessage } from "formik";
import React from "react";
import * as Yup from "yup";
import { useAuth } from "../contexts/AuthContext";
import { Link } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
export default function signup() {
  const initialValues = {
    email: "",
    password: "",
    confirmPassword: "",
  };
  const { signup, currentUser } = useAuth();
  const validationSchema = Yup.object({
    email: Yup.string().email("Invalid email address").required("Required"),
    password: Yup.string()
      .min(8, "Must have a minimum of 8 characters")
      .required("Required"),
    confirmPassword: Yup.string()
      .min(8, "Must have a minimum of 8 characters")
      .oneOf([Yup.ref("password")], "Values must be same")
      .required("Required"),
  });

  async function handleSubmit(email, password) {
    toast.promise(
      signup(email, password),
      {
        pending: "Signing up...",
        success: "Signup successful",
        error: "Signup failed",
      },
      {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
      }
    );
  }

  return (
    <div className="min-h-screen flex flex-col gap-10 justify-center items-center">
      <h1 className="text-6xl">Sign Up</h1>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={(values) => {
          handleSubmit(values.email, values.password);
        }}
      >
        <Form className="flex flex-col  text-2xl border  rounded-md shadow-md p-12 gap-3">
          <label htmlFor="email">Email</label>
          <Field name="email" type="text" className="border  rounded-md p-3" />
          <ErrorMessage name="email" className="text-red-800" />

          <label htmlFor="password">Password</label>
          <Field
            name="password"
            type="password"
            className="border  rounded-md p-3"
          />
          <ErrorMessage name="password" />

          <label htmlFor="confirmPassword">Confirm password</label>
          <Field
            name="confirmPassword"
            type="password"
            className="border  rounded-md p-3"
          />
          <ErrorMessage name="confirmPassword" />

          <button
            type="submit"
            className="text-white bg-blue-700 p-4 rounded-md hover:bg-blue-600"
          >
            Submit
          </button>
          <ToastContainer />
        </Form>
      </Formik>
      Already have an account? <Link to="/login">Log In</Link>
    </div>
  );
}
