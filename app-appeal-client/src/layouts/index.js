import React from "react";
import 'react-toastify/dist/ReactToastify.css';
import AdminNavigation from "../components/AdminNavigation";
import {ToastContainer} from "react-toastify";

function BasicLayout(props) {
  return (
    <div>
      <ToastContainer/>
      <AdminNavigation/>

      {props.children}
    </div>
  );
}

export default BasicLayout;
