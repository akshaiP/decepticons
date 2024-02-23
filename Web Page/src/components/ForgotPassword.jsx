import React from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { ToastContainer, toast } from "react-toastify";
import { useAuth } from "../contexts/AuthContext";
function ForgotPassword() {
  const { resetPassword } = useAuth();
  const initialValues = {
    email: "",
  };

  const handleSubmit = async ({ email }) => {
    toast.promise(
      resetPassword(email),
      {
        pending: "Sending email ..",
        success: "Email sent successfully",
        error: "Email not sent successfully",
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
  };
  const emailSchema = Yup.object({
    email: Yup.string().email().required("Required"),
  });

  return (
    <div className="flex items-center justify-center h-screen bg-gray-200">
      <div className="p-6 max-w-sm w-full bg-white shadow-md rounded-md">
        <div className="flex justify-center items-center">
          <span className="text-gray-700 font-semibold text-2xl">
            Reset Password
          </span>
        </div>
        <Formik
          initialValues={initialValues}
          onSubmit={handleSubmit}
          validationSchema={emailSchema}
        >
          <Form className="mt-4">
            <label className="block">
              <span className="text-gray-700 text-sm">Email</span>
              <Field
                type="text"
                name="email"
                className="form-input mt-1 block w-full rounded-md border p-4    "
                placeholder="your email"
              />
            </label>
            <div className="text-red-800">
              <ErrorMessage name="email" />
            </div>

            <div className="mt-6">
              <button
                type="submit"
                className="p-4 text-center bg-blue-600 rounded-md w-full text-white text-sm hover:bg-blue-500 focus:outline-none"
              >
                Send Reset Link
              </button>
            </div>
          </Form>
        </Formik>
      </div>
      <ToastContainer />
    </div>
  );
}

export default ForgotPassword;
