import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
const firebaseConfig = {
  apiKey: "AIzaSyCNzahA5qAPBYpUahpl8qOi0NU48vqOb90",

  authDomain: "sample-f3e8a.firebaseapp.com",

  projectId: "sample-f3e8a",

  storageBucket: "sample-f3e8a.appspot.com",

  messagingSenderId: "1058201690563",

  appId: "1:1058201690563:web:1d7a0201a9d8cc6cdbdd76",

  measurementId: "G-65QFHN9SV9",
};

const app = initializeApp(firebaseConfig);

export const auth = getAuth();
export default app;
