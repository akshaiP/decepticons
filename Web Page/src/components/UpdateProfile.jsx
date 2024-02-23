import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { useAuth } from "../contexts/AuthContext";
import { ToastContainer, toast } from "react-toastify";

const validationSchema = Yup.object({
  email: Yup.string().email("Invalid email address").required("Required"),
  password: Yup.string()
    .min(6, "Password must be at least 6 characters")
    .required("Required"),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref("password"), null], "Passwords must match")
    .required("Required"),
});

function UpdateProfile() {
  const { updateEmailUser, updatePasswordUser } = useAuth();
  async function updateEmailAndPassword(email, password) {
    try {
      await updateEmailUser(email);
      await updatePasswordUser(password);
      return "Update successful";
    } catch (error) {
      throw Error("Update failed");
    }
  }

  const handleSubmit = async ({ email, password }) => {
    toast.promise(
      updateEmailAndPassword(email, password),
      {
        pending: "Updating ..",
        success: "Update successful",
        error: "Update failed",
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

  return (
    <div className="flex items-center justify-center h-screen bg-gray-200">
      <div className="p-6 max-w-sm w-full bg-white shadow-md rounded-md">
        <div className="flex justify-center items-center">
          <span className="text-gray-700 font-semibold text-2xl">
            Update Profile
          </span>
        </div>
        <Formik
          initialValues={{ email: "", password: "", confirmPassword: "" }}
          validationSchema={validationSchema}
          onSubmit={(values) => {
            handleSubmit(values.email, values.password);
          }}
        >
          {({ errors, touched }) => (
            <Form className="mt-4">
              <label className="block">
                <span className="text-gray-700 text-sm">Email</span>
                <Field
                  type="email"
                  name="email"
                  className={`form-input mt-1 block w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 ${
                    errors.email && touched.email ? "border-red-500" : ""
                  }`}
                  placeholder="your email"
                />
                <ErrorMessage
                  name="email"
                  component="div"
                  className="text-red-500 text-sm mt-1"
                />
              </label>
              <label className="block mt-3">
                <span className="text-gray-700 text-sm">Password</span>
                <Field
                  type="password"
                  name="password"
                  className={`form-input mt-1 block w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 ${
                    errors.password && touched.password ? "border-red-500" : ""
                  }`}
                  placeholder="your password"
                />
                <ErrorMessage
                  name="password"
                  component="div"
                  className="text-red-500 text-sm mt-1"
                />
              </label>
              <label className="block mt-3">
                <span className="text-gray-700 text-sm">Confirm Password</span>
                <Field
                  type="password"
                  name="confirmPassword"
                  className={`form-input mt-1 block w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 ${
                    errors.confirmPassword && touched.confirmPassword
                      ? "border-red-500"
                      : ""
                  }`}
                  placeholder="confirm your password"
                />
                <ErrorMessage
                  name="confirmPassword"
                  component="div"
                  className="text-red-500 text-sm mt-1"
                />
              </label>
              <div className="mt-6">
                <button
                  type="submit"
                  className="py-2 px-4 text-center bg-blue-600 rounded-md w-full text-white text-sm hover:bg-blue-500 focus:outline-none"
                >
                  Update Profile
                </button>
              </div>
            </Form>
          )}
        </Formik>
      </div>
      <ToastContainer />
    </div>
  );
}

export default UpdateProfile;
