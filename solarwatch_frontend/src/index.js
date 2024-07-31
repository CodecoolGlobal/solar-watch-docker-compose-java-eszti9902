import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { RouterProvider, createBrowserRouter, r } from 'react-router-dom';
import Register from './Pages/Register/Register';
import Login from './Pages/Login/Login';
import Solarwatch from './Pages/Solarwatch/Solarwatch';
import AdminMainPage from './Pages/Mainpage/AdminMainPage';
import SolarwatchEditor from './Pages/SolarWatchEditor/SolarwatchEditor';
import { AuthProvider } from './AuthProvider';
import { ProtectedRoute } from './ProtectedRoute';

const router = createBrowserRouter(
  [
    {
      children: [
        {
          path: "/",
          element: <App />
        },
        {
          path: "/solar-watch",
          element: <Solarwatch />
        },
        {
          path: "/admin/solarwatch",
          element: (
            < ProtectedRoute role={"ROLE_ADMIN"}>
              <AdminMainPage />
            </ProtectedRoute>
          )
        },
        // {
        //   path: "/admin/solarwatch/create",
        //   element: 
        // },
        {
          path: "/admin/solarwatch/edit/:cityId",
          element: (
            < ProtectedRoute role={"ROLE_ADMIN"}>
              <SolarwatchEditor />
            </ProtectedRoute>
          )
        },
        // {
        //   path: "/admin/solarwatch/delete/:cityId",
        //   element: 
        // },
        {
          path: "/user/register",
          element: <Register />
        },
        {
          path: "/user/login",
          element: <Login />
        },
        // {
        //   path: "/user/promote/:username",
        //   element:
        // },
        // {
        //   path: "/user/unpromote/:username",
        //   element: 
        // }
      ]
    }
  ]
)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router}></RouterProvider>
    </AuthProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
