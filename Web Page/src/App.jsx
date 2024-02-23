import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import Signup from "./components/Signup";
import { AuthProvider, useAuth } from "./contexts/AuthContext";
import { Routes, Route, Navigate } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Login from "./components/Login";
import ForgotPassword from "./components/ForgotPassword";
import UpdateProfile from "./components/UpdateProfile";

function PrivateRoute({ children }) {
  const { currentUser } = useAuth();

  return currentUser ? children : <Navigate to="/login" />;
}

function AntiPrivateRoute({ children }) {
  const { currentUser } = useAuth();
  return !currentUser ? children : <Navigate to="/dashboard" />;
}

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route
          path="/"
          element={
            <AntiPrivateRoute>
              <Signup />
            </AntiPrivateRoute>
          }
        />
        <Route
          path="/dashboard"
          element={
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          }
        />
        <Route
          path="/login"
          element={
            <AntiPrivateRoute>
              <Login />
            </AntiPrivateRoute>
          }
        />
        <Route path="/resetPassword" element={<ForgotPassword />} />
        <Route path="/updateProfile" element={<UpdateProfile />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
