import { Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import LoginPage from "./page/login/LoginPage";
import AdminLayout from "./layout/AdminLayout";
import WsUrl from "./utils/constants/WsUrl";
import Dashboard from "./page/dashboard/Dashboard";
import Topbar from "./component/topbar/Topbar";
import Sidebar from "./component/sidebar/Sidebar";
import OrderPage from "./page/order/OrderPage";
import { useEffect, useState } from "react";

function App() {

  const [enable, setEneble] = useState(false)

  return (
    <div className="page-top">
      <div className="wrapper">
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="" element={<AdminLayout />} >
            <Route path="/" element={<Dashboard />} />
            <Route path='/order' element={<OrderPage />} />
          </Route>
        </Routes>
        <ToastContainer />
      </div>
    </div>

  );
}

export default App;
