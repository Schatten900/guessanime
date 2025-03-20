import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "../src/styles/index.css";
import Register from "./pages/Register.jsx";
import Login from "./pages/Login.jsx";
import Home from "./pages/Home.jsx";
import RoundByCharacter from "./pages/RoundByCharacter.jsx";
import Alimentation from "./pages/Alimentation.jsx";
import Leader from "./pages/Leader.jsx";
import RequireAuth from "./components/RequireAuth.jsx";

const router = createBrowserRouter([
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
  {
    path: "/",
    element: <RequireAuth><Home/></RequireAuth>
  },
  {
    path: "/byCharacter",
    element: <RequireAuth><RoundByCharacter/></RequireAuth>
  },
  {
    path:"/alimentation",
    element: <RequireAuth><Alimentation/></RequireAuth>
  },
  {
    path:"/ranking",
    element: <RequireAuth><Leader/></RequireAuth>
  }
]);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
