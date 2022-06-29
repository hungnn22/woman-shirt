import { Routes, Route, Outlet } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import LoginPage from "./page/login/LoginPage";
import AdminLayout from "./layout/AdminLayout";

import WsUrl from "./utils/constants/WsUrl";
import Topbar from "./component/topbar/Topbar";
import Sidebar from "./component/sidebar/Sidebar";
import OrderPage from "./page/order/OrderPage";
import Dashboard from "./page/dashboard/Dashboard";
import ProductType from "./page/product/ProductType";
import { useEffect, useState } from "react";

function App() {

  return (
    <div className="page-top">
      <div className="wrapper">
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="" element={<AdminLayout />} >

            <Route path="/dashboard" element={<Dashboard />} />
            <Route path='/order' element={<OrderPage />} />
            <Route path='/producttype' element={<ProductType />} />

          </Route>
        </Routes>
        <ToastContainer />
      </div>
    </div>

  );
}

export default App;
