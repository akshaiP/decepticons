import React from "react";
import { useAuth } from "../contexts/AuthContext";
import { useNavigate, Navigate, Link } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import calendar from "../assets/calendar.png";
import { HoverEffect } from "./CardHoverEffect";
import { Meteors } from "./Meteors";
export default function Dashboard() {
  const projects = [
    {
      title: "Voice call",
      description: "Create a voice call to the doctor directly",
      image: calendar,
      link: "tel:+18603176113",
    },
    {
      title: "Chatbot",
      description:
        " Chat directly with an ai chatbot that can ensure your health condition properly",
      image: calendar,
      link: "https://mbot.z29.web.core.windows.net",
    },
    {
      title: "Video call",
      description: "Have  a direct conversation with the doctor",
      image: calendar,
      link: "https://meet.google.com/dfu-dmrk-ird",
    },
    {
      title: "Map",
      description: "Locate the doctor's location properly",
      image: calendar,
      link: "https://www.google.com/maps/search/psychologists+near+me",
    },
    {
      title: "Calender",
      description:
        "Make a booking appointment with the doctor whenever you need",
      image: "",
      link: "https://calendar.google.com",
    },
    {
      title: "Appoinment",
      description: "Book an appoinment",
      image: calendar,
      link: "https://docs.google.com/forms/d/e/1FAIpQLSd6EvRLg4AQie0jblx9zJRCAob3Kedu_OsfrGvKMZXnEvB8yA/viewform",
    },
  ];
  const navigate = useNavigate();
  const { currentUser, logout } = useAuth();
  async function handleLogout() {
    try {
      await logout();
      navigate("/login");
    } catch (err) {
      console.log(err);
      toast.error(`Logout failed: ${err.message}`, {
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
  function navigator() {
    useNavigate("/updateProfile");
  }

  return (
    <>
      <div className="flex shadow-md p-4 justify-between items-center">
        <h1 className="text-3xl text-white">Mental Health screening </h1>
        <button
          className="p-2 bg-blue-700 text-white rounded-md "
          onClick={handleLogout}
        >
          logout
        </button>
      </div>
      <div className="min-h-screen flex  justify-center items-center  ">
        <HoverEffect items={projects} />
        <ToastContainer />
        <Meteors number={50} />
      </div>
    </>
  );
}
